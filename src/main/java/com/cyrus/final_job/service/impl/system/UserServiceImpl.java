package com.cyrus.final_job.service.impl.system;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cyrus.final_job.dao.*;
import com.cyrus.final_job.dao.system.RoleDao;
import com.cyrus.final_job.dao.system.UserDao;
import com.cyrus.final_job.dao.system.UserRoleDao;
import com.cyrus.final_job.entity.Holiday;
import com.cyrus.final_job.entity.Position;
import com.cyrus.final_job.entity.base.Result;
import com.cyrus.final_job.entity.base.ResultPage;
import com.cyrus.final_job.entity.condition.UserAccountCondition;
import com.cyrus.final_job.entity.condition.UserAccountQueryCondition;
import com.cyrus.final_job.entity.condition.UserCondition;
import com.cyrus.final_job.entity.system.Role;
import com.cyrus.final_job.entity.system.User;
import com.cyrus.final_job.entity.system.UserRole;
import com.cyrus.final_job.entity.vo.UserAccountVo;
import com.cyrus.final_job.entity.vo.UserDetailVo;
import com.cyrus.final_job.enums.*;
import com.cyrus.final_job.service.system.UserService;
import com.cyrus.final_job.utils.CommonUtils;
import com.cyrus.final_job.utils.DateUtils;
import com.cyrus.final_job.utils.Results;
import com.cyrus.final_job.utils.UserUtils;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 用户表(User)表服务实现类
 *
 * @author cyrus
 * @since 2020-02-16 14:53:32
 */
@Service("userService")
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private UserRoleDao userRoleDao;

    @Autowired
    private NationDao nationDao;

    @Autowired
    private PoliticsStatusDao politicsStatusDao;

    @Autowired
    private DepartmentDao departmentDao;

    @Autowired
    private PositionDao positionDao;

    @Autowired

    private HolidayDao holidayDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public User queryById(Integer id) {
        return this.userDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<User> queryAllByLimit(int offset, int limit) {
        return this.userDao.queryAllByLimit(offset, limit);
    }

    @Override
    public ResultPage getStaff(JSONObject params) {
        UserCondition userCondition = params.toJavaObject(UserCondition.class);
        userCondition.buildLimit();
        List<UserDetailVo> userDetailVoList = userDao.queryStaffByCondition(userCondition);
        buildUserDetailVo(userDetailVoList);
        Long total = userDao.queryStaffCountByCondition(userCondition);
        return Results.createOk(total, userDetailVoList);
    }

    @Override
    public ResultPage getUsers(JSONObject params) {
        UserAccountQueryCondition condition = params.toJavaObject(UserAccountQueryCondition.class);
        condition.buildLimit();
        List<UserAccountVo> userAccountVos = userDao.queryUserAccountByCondition(condition);
        Long total = userDao.queryUserAccountCountByCondition(condition);
        for (UserAccountVo userAccountVo : userAccountVos) {
            UserRole userRole = new UserRole();
            userRole.setUserId(userAccountVo.getId());
            List<UserRole> userRoles = userRoleDao.queryAll(userRole);
            List<Role> list = new ArrayList<>();
            for (UserRole role : userRoles) {
                Role r = roleDao.queryById(role.getRoleId());
                list.add(r);
            }
            userAccountVo.setEnabledStr(EnableBooleanEnum.getEnumByCode(userAccountVo.getEnabled()).getDesc());
            userAccountVo.setRoles(list);
        }
        return Results.createOk(total, userAccountVos);
    }

    @Override
    public Result importStaff(MultipartFile file) {
        List<User> importData = new ArrayList<>();
        User user = null;
        // 导入数据构造
        try {
            HSSFWorkbook workbook = new HSSFWorkbook(file.getInputStream());
            // 获取 sheet 个数，这里一般只有一个
            int numberOfSheets = workbook.getNumberOfSheets();
            for (int i = 0; i < numberOfSheets; i++) {
                HSSFSheet sheet = workbook.getSheetAt(i);
                // sheet 内的行数
                int rows = sheet.getPhysicalNumberOfRows();
                for (int j = 1; j < rows; j++) {
                    HSSFRow row = sheet.getRow(j);
                    // 列数
                    int cells = row.getPhysicalNumberOfCells();
                    user = new User();
                    for (int k = 0; k < cells; k++) {
                        // 获取单元格
                        HSSFCell cell = row.getCell(k);
                        // 对每个单元格进行判断
                        judgeCellValueType(cell, user, k);
                    }
                    user.setUpdateTime(DateUtils.getNowTime());
                    user.setUsername(CommonUtils.convertHanzi2Pinyin(user.getRealName(), true));
                    user.setPassword(CommonUtils.getPassword(CommonUtils.convertHanzi2Pinyin(user.getRealName(), true)));
                    user.setUserFace("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=3684533571,3875739943&fm=26&gp=0.jpg");
                    importData.add(user);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Results.error("导入失败，请检查导入数据是否正确");
        }
        int successCount = 0;
        List<Integer> errIndex = new ArrayList<>();
        for (int i = 0; i < importData.size(); i++) {
            if (userDao.insert(importData.get(i)) > 0) {
                successCount++;
            } else {
                errIndex.add(i + 1);
            }
        }
        if (successCount == importData.size()) {
            return Results.createOk("全部导入成功");
        }
        return Results.createOk("部分导入成功,成功 [" + successCount + "] 条;以下记录添加失败:[" + errIndex.toString() + "],请检查这些数据是否正确");
    }


    private void judgeCellValueType(HSSFCell cell, User user, int k) {
        switch (cell.getCellType()) {
            case STRING:
                String cellValue = cell.getStringCellValue();
                // 判断是哪个单元格
                switch (k) {
                    case 1:
                        user.setRealName(cellValue);
                        break;
                    case 2:
                        user.setWorkId(Long.parseLong(cellValue));
                        break;
                    case 3:
                        user.setGender(GenderEnum.getEnumByDesc(cellValue).getCode());
                        break;
                    case 5:
                        user.setIdCard(cellValue);
                        break;
                    case 6:
                        user.setWedlock(WedlockEnum.getEnumByDesc(cellValue).getCode());
                        break;
                    case 7:
                        user.setNationId(nationDao.getNationIdByName(cellValue));
                        break;
                    case 8:
                        user.setNativePlace(cellValue);
                        break;
                    case 9:
                        user.setPoliticsId(politicsStatusDao.getIdByName(cellValue));
                        break;
                    case 10:
                        user.setPhone(cellValue);
                        break;
                    case 11:
                        user.setAddress(cellValue);
                        break;
                    case 14:
                        cellValue = cellValue.substring(0, cellValue.length() - 1);
                        user.setWorkAge(Double.parseDouble(cellValue));
                        break;
                    case 18:
                        user.setTopDegree(DegreeEnum.getEnumByDesc(cellValue).getCode());
                        break;
                    case 19:
                        user.setSpecialty(cellValue);
                        break;
                    case 20:
                        user.setSchool(cellValue);
                        break;
                    case 22:
                        user.setWorkState(WorkStateEnum.getEnumByDesc(cellValue).getCode());
                        break;
                    case 23:
                        user.setEmail(cellValue);
                        break;
                    case 28:
                        user.setEnabled(EnableBooleanEnum.getEnumByDesc(cellValue).getCode());
                        break;
                }
                break;
            default: {
                switch (k) {
                    case 2:
                        user.setWorkId(CommonUtils.doubleToLong(cell.getNumericCellValue()));
                        break;
                    case 4:
                        if (cell.getDateCellValue() == null) {
                            break;
                        }
                        user.setBirthday(new Timestamp(cell.getDateCellValue().getTime()));
                        break;
                    case 12:
                        user.setDepartmentId(CommonUtils.doubleToInt(cell.getNumericCellValue()));
                        break;
                    case 15:
                        user.setPositionId(CommonUtils.doubleToInt(cell.getNumericCellValue()));
                        break;
                    case 21:
                        if (cell.getDateCellValue() == null) {
                            break;
                        }
                        user.setCreateTime(new Timestamp(cell.getDateCellValue().getTime()));
                        break;
                    case 24:
                        if (cell.getDateCellValue() == null) {
                            break;
                        }
                        user.setBeginContractTime(new Timestamp(cell.getDateCellValue().getTime()));
                        break;
                    case 25:
                        if (cell.getDateCellValue() == null) {
                            break;
                        }
                        user.setEndContractTime(new Timestamp(cell.getDateCellValue().getTime()));
                        break;
                    case 26:
                        if (cell.getDateCellValue() == null) {
                            break;
                        }
                        user.setConversionTime(new Timestamp(cell.getDateCellValue().getTime()));
                        break;
                    case 27:
                        if (cell.getDateCellValue() == null) {
                            break;
                        }
                        user.setDepartureTime(new Timestamp(cell.getDateCellValue().getTime()));
                        break;
                }
            }
            break;
        }
    }

    @Override
    public ResponseEntity<byte[]> exportStaff() {
        List<UserDetailVo> exportData = userDao.export();
        buildUserDetailVo(exportData);
        // 导出数据构造
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 样式
        HSSFCellStyle dateCellStyle = workbook.createCellStyle();
        dateCellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy"));
        // 创建表单
        HSSFSheet sheet = workbook.createSheet();
        // 标题行
        buildTitle(sheet);
        // 构建内容行
        buildContent(sheet, exportData, dateCellStyle);
        HttpHeaders headers = new HttpHeaders();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        try {
            headers.setContentDispositionFormData("attachment",
                    new String("员工基础信息.xls".getBytes("utf-8"), "ISO-8859-1"));
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            workbook.write(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<byte[]>(stream.toByteArray(), headers, HttpStatus.CREATED);
    }

    private void buildContent(HSSFSheet sheet, List<UserDetailVo> exportData, HSSFCellStyle dateCellStyle) {
        for (int i = 0; i < exportData.size(); i++) {
            UserDetailVo item = exportData.get(i);
            HSSFRow row = sheet.createRow(i + 1);
            row.createCell(0).setCellValue(item.getId());
            row.createCell(1).setCellValue(item.getRealName());
            row.createCell(2).setCellValue(item.getWorkId());
            row.createCell(3).setCellValue(item.getGenderStr());
            HSSFCell cell4 = row.createCell(4);
            cell4.setCellStyle(dateCellStyle);
            cell4.setCellValue(item.getBirthday());
            row.createCell(5).setCellValue(item.getIdCard());
            row.createCell(6).setCellValue(item.getWedlockStr());
            row.createCell(7).setCellValue(item.getNationName());
            row.createCell(8).setCellValue(item.getNativePlace());
            row.createCell(9).setCellValue(item.getPoliticsStr());
            row.createCell(10).setCellValue(item.getPhone());
            row.createCell(11).setCellValue(item.getAddress());
            row.createCell(12).setCellValue(item.getDepartmentId());
            row.createCell(13).setCellValue(item.getDepartmentName());
            row.createCell(14).setCellValue(item.getWorkAgeStr());
            row.createCell(15).setCellValue(item.getPositionId());
            row.createCell(16).setCellValue(item.getPositionName());
            row.createCell(17).setCellValue(item.getPositionLevelName());
            row.createCell(18).setCellValue(item.getTopDegreeStr());
            row.createCell(19).setCellValue(item.getSpecialty());
            row.createCell(20).setCellValue(item.getSchool());
            HSSFCell cell19 = row.createCell(21);
            cell19.setCellStyle(dateCellStyle);
            cell19.setCellValue(item.getCreateTime());
            row.createCell(22).setCellValue(item.getWorkStateStr());
            row.createCell(23).setCellValue(item.getEmail());
            HSSFCell cell24 = row.createCell(24);
            cell24.setCellStyle(dateCellStyle);
            cell24.setCellValue(item.getBeginContractTime());
            HSSFCell cell25 = row.createCell(25);
            cell25.setCellStyle(dateCellStyle);
            cell25.setCellValue(item.getEndContractTime());
            HSSFCell cell26 = row.createCell(26);
            cell26.setCellStyle(dateCellStyle);
            cell26.setCellValue(item.getConversionTime());
            HSSFCell cell27 = row.createCell(27);
            cell27.setCellStyle(dateCellStyle);
            cell27.setCellValue(item.getDepartureTime());
            row.createCell(28).setCellValue(item.getEnabledStr());
        }
    }


    public void buildTitle(HSSFSheet sheet) {
        HSSFRow row0 = sheet.createRow(0);
        HSSFCell c0 = row0.createCell(0);
        c0.setCellValue("编号");
        HSSFCell c1 = row0.createCell(1);
        c1.setCellValue("姓名");
        HSSFCell c2 = row0.createCell(2);
        c2.setCellValue("工号");
        HSSFCell c3 = row0.createCell(3);
        c3.setCellValue("性别");
        HSSFCell c4 = row0.createCell(4);
        c4.setCellValue("出生日期");
        HSSFCell c5 = row0.createCell(5);
        c5.setCellValue("身份证号码");
        HSSFCell c6 = row0.createCell(6);
        c6.setCellValue("婚姻状况");
        HSSFCell c7 = row0.createCell(7);
        c7.setCellValue("民族");
        HSSFCell c8 = row0.createCell(8);
        c8.setCellValue("籍贯");
        HSSFCell c9 = row0.createCell(9);
        c9.setCellValue("政治面貌");
        HSSFCell c10 = row0.createCell(10);
        c10.setCellValue("电话号码");
        HSSFCell c11 = row0.createCell(11);
        c11.setCellValue("联系地址");
        HSSFCell c12 = row0.createCell(12);
        c12.setCellValue("所属部门id");
        HSSFCell c13 = row0.createCell(13);
        c13.setCellValue("所属部门");
        HSSFCell c14 = row0.createCell(14);
        c14.setCellValue("工龄");
        HSSFCell c15 = row0.createCell(15);
        c15.setCellValue("职位id");
        HSSFCell c16 = row0.createCell(16);
        c16.setCellValue("职位");
        HSSFCell c17 = row0.createCell(17);
        c17.setCellValue("职位级别");
        HSSFCell c18 = row0.createCell(18);
        c18.setCellValue("最高学历");
        HSSFCell c19 = row0.createCell(19);
        c19.setCellValue("专业");
        HSSFCell c20 = row0.createCell(20);
        c20.setCellValue("毕业院校");
        HSSFCell c21 = row0.createCell(21);
        c21.setCellValue("入职日期");
        HSSFCell c22 = row0.createCell(22);
        c22.setCellValue("在职状态");
        HSSFCell c23 = row0.createCell(23);
        c23.setCellValue("邮箱");
        HSSFCell c24 = row0.createCell(24);
        c24.setCellValue("合同起始日期");
        HSSFCell c25 = row0.createCell(25);
        c25.setCellValue("合同终止日期");
        HSSFCell c26 = row0.createCell(26);
        c26.setCellValue("转正日期");
        HSSFCell c27 = row0.createCell(27);
        c27.setCellValue("离职日期");
        HSSFCell c28 = row0.createCell(28);
        c28.setCellValue("账号是否可用");
    }

    private void buildUserDetailVo(List<UserDetailVo> userDetailVoList) {
        for (UserDetailVo item : userDetailVoList) {
            item.setGenderStr(GenderEnum.getEnumByCode(item.getGender()).getDesc());
            item.setWedlockStr(WedlockEnum.getEnumByCode(item.getWedlock()).getDesc());
            item.setNationName(nationDao.queryById(item.getNationId()).getName());
            item.setPoliticsStr(politicsStatusDao.queryById(item.getPoliticsId()).getName());
            if (departmentDao.queryById(item.getDepartmentId()) != null) {
                item.setDepartmentName(departmentDao.queryById(item.getDepartmentId()).getName());
            }
            Position position = positionDao.queryById(item.getPositionId());
            item.setPositionName(position.getPositionName());
            item.setTopDegreeStr(DegreeEnum.getEnumByCode(item.getTopDegree()).getDesc());
            item.setWorkStateStr(WorkStateEnum.getEnumByCode(item.getWorkState()).getDesc());
            item.setPositionLevelName(PositionLevelEnum.getEnumByCode(position.getPositionLevel()).getDesc());
            item.setWorkAgeStr(item.getWorkAge() + "年");
            item.setEnabledStr(Boolean.TRUE.equals(item.getEnabled()) ? "启用" : "禁用");
            if (Objects.isNull(item.getContractTerm())) {
                item.setContractTermStr("");
            } else {
                item.setContractTermStr(item.getContractTerm() + "年");
            }
        }
    }

    /**
     * 新增数据
     *
     * @param user 实例对象
     * @return 实例对象
     */
    @Override
    public User insert(User user) {
        this.userDao.insert(user);
        return user;
    }

    /**
     * 修改数据
     *
     * @param user 实例对象
     * @return 实例对象
     */
    @Override
    public User update(User user) {
        this.userDao.update(user);
        return this.queryById(user.getId());
    }

    @Override
    public Result updateStaff(JSONObject params) {
        User user = params.toJavaObject(User.class);
        Result result = user.checkBaseParams();
        if (result != null) return result;
        if (Objects.equals(user.getId(), 1) && !Objects.equals(UserUtils.getCurrentUserId(), 1)) {
            return Results.error("只有 admin 账户能更新 admin 用户");
        }
        user.setUpdateTime(DateUtils.getNowTime());

        User old = userDao.queryById(user.getId());

        userDao.update(user);

        // 如果老记录账号是禁用的，新纪录是启用的，说明员工复职，重新制定假期
        if (EnableBooleanEnum.DISABLE.getCode().equals(old.isEnabled()) &&
                EnableBooleanEnum.ENABLED.getCode().equals(user.isEnabled())) {
            buildHoliday(user);
        }
        return Results.createOk("更新成功");
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.userDao.deleteById(id) > 0;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.loadUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("用户不存在");
        }

        // 根据用户id 从 user_role 表中查找出所有的 role id;
        UserRole userRole = new UserRole();
        userRole.setUserId(user.getId());
        List<UserRole> userRoles = userRoleDao.queryAll(userRole);
        List<Integer> roleIds = userRoles.stream().map(item -> item.getRoleId()).collect(Collectors.toList());
        // 获取该用户下的所有角色信息
        List<Role> roles = roleDao.getRolesByIds(roleIds);
        user.setRoles(roles);
        return user;
    }

    @Override
    public Result addStaff(JSONObject params) {
        User user = params.toJavaObject(User.class);
        user.checkParams();
        // 工号
        Long maxWorkId = userDao.getMaxWorkId();
        user.setWorkId(maxWorkId + 1);
        user.setWorkState(WorkStateEnum.IN.getCode());
        userDao.insert(user);
        // 员工入职时初始化其假期
        buildHoliday(user);
        return Results.createOk("添加成功");
    }

    private void buildHoliday(User user) {

        holidayDao.deleteByUserId(user.getId());

        Holiday holiday = new Holiday();
        // 初始化调休假期
        holiday.setUserId(user.getId());
        holiday.setHolidayType(HolidayTypeEnum.EXCHANGE.getCode());
        holiday.setHolidayTime(0);
        holiday.setRemaining(0);
        holiday.setCreateTime(String.valueOf(LocalDate.now().getYear()));
        holidayDao.insert(holiday);
        // 初始化病假
        holiday.setHolidayType(HolidayTypeEnum.SICK_LEAVE.getCode());
        holiday.setHolidayTime(5);
        holiday.setRemaining(5);
        holiday.setCreateTime(String.valueOf(LocalDate.now().getYear()));
        holidayDao.insert(holiday);
        // 初始化年假，工龄0-5年休5天，5-10年修10天，10-20年修15天，20-无穷大，休20天
        holiday.setHolidayType(HolidayTypeEnum.ANNUAL_LEAVE.getCode());
        if (user.getWorkAge() >= 0 && user.getWorkAge() <= 5) {
            holiday.setHolidayTime(5);
            holiday.setRemaining(5);
        } else if (user.getWorkAge() > 5 && user.getWorkAge() <= 10) {
            holiday.setHolidayTime(10);
            holiday.setRemaining(10);
        } else if (user.getWorkAge() > 10 && user.getWorkAge() <= 20) {
            holiday.setHolidayTime(15);
            holiday.setRemaining(15);
        } else if (user.getWorkAge() > 20) {
            holiday.setHolidayTime(20);
            holiday.setRemaining(20);
        } else {
            holiday.setHolidayTime(0);
            holiday.setRemaining(0);
        }
        holiday.setCreateTime(String.valueOf(LocalDate.now().getYear()));
        holidayDao.insert(holiday);
        // 初始化哺乳假，女方两个月，男方一礼拜
        holiday.setHolidayType(HolidayTypeEnum.BREASTFEEDING_LEAVE.getCode());
        if (Objects.equals(GenderEnum.WOMAN.getCode(), user.getGender())) {
            holiday.setHolidayTime(60);
            holiday.setRemaining(60);
        } else if (Objects.equals(GenderEnum.MAN.getCode(), user.getGender())) {
            holiday.setHolidayTime(7);
            holiday.setRemaining(7);
        } else {
            holiday.setHolidayTime(0);
            holiday.setRemaining(0);
        }
        holiday.setCreateTime(String.valueOf(LocalDate.now().getYear()));
        holidayDao.insert(holiday);
        // 初始化婚假，针对未结婚员工
        holiday.setHolidayType(HolidayTypeEnum.MARRIAGE_HOLIDAY.getCode());
        if (!Objects.equals(WedlockEnum.MARRIED.getCode(), user.getWedlock())) {
            holiday.setHolidayTime(7);
            holiday.setRemaining(7);
        } else {
            holiday.setHolidayTime(0);
            holiday.setRemaining(0);
        }
        holiday.setCreateTime(String.valueOf(LocalDate.now().getYear()));
        holidayDao.insert(holiday);
        // 初始化丧假
        holiday.setHolidayType(HolidayTypeEnum.FUNERAL_LEAVE.getCode());
        holiday.setHolidayTime(7);
        holiday.setRemaining(7);
        holiday.setCreateTime(String.valueOf(LocalDate.now().getYear()));
        holidayDao.insert(holiday);
    }

    @Override
    public Result delStaff(JSONObject params) {
        Integer id = params.getInteger("id");
        if (Objects.isNull(id)) {
            return Results.error("id 不能为空");
        }
        if (Objects.equals(id, 1)) {
            return Results.error("admin 账号不能删除");
        }
        int userId = UserUtils.getCurrentUserId();
        if (Objects.equals(id, userId)) {
            return Results.error("您不能删除自己");
        }
        userDao.deleteById(id);
        userRoleDao.deleteByUserId(id);
        return Results.createOk("删除成功");
    }


    @Override
    public Result delStaffs(JSONObject params) {
        JSONArray array = params.getJSONArray("ids");
        if (array == null || array.isEmpty()) return Results.error("ids 参数不能为空");
        List<Integer> ids = JSONArray.parseArray(JSONObject.toJSONString(array), Integer.class);
        for (Integer id : ids) {
            if (Objects.equals(id, 1)) {
                return Results.error("admin 账号不能删除");
            }
            if (Objects.equals(id, UserUtils.getCurrentUserId())) {
                return Results.error("您不能删除自己");
            }
        }
        for (Integer id : ids) {
            userDao.deleteById(id);
            userRoleDao.deleteByUserId(id);
        }
        return Results.createOk("删除成功");
    }

    @Override
    public Result updateUserAccount(UserAccountCondition condition) {
        if (condition == null || condition.getId() == null) return Results.createInvalidParam();
        if (Objects.equals(condition.getId(), 1) && !Objects.equals(UserUtils.getCurrentUserId(), 1)) {
            return Results.error("非admin账号不能操作admin账号");
        }
        Integer[] roleIds = condition.getRoleIds();
        if (roleIds != null && roleIds.length > 0) {
            userRoleDao.deleteByUserId(condition.getId());
            for (Integer roleId : roleIds) {
                UserRole userRole = new UserRole();
                userRole.setUserId(condition.getId());
                userRole.setRoleId(roleId);
                userRoleDao.insert(userRole);
            }
        }
        User user = new User();
        if (condition.getPassword() != null) {
            String password = CommonUtils.getPassword(condition.getPassword());
            user.setPassword(password);
        }
        user.setId(condition.getId());
        if (condition.getEnabled() != null) {
            user.setEnabled(condition.getEnabled());
        }
        userDao.update(user);
        return Results.createOk("账号更新成功");
    }
}