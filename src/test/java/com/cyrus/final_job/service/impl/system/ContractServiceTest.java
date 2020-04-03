package com.cyrus.final_job.service.impl.system;

import com.cyrus.final_job.dao.ContractDao;
import com.cyrus.final_job.dao.system.UserDao;
import com.cyrus.final_job.entity.Contract;
import com.cyrus.final_job.entity.system.User;
import com.cyrus.final_job.enums.ConfirmStateEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class ContractServiceTest {

    @Autowired
    private ContractDao contractDao;

    @Autowired
    private UserDao userDao;


    @Test
    public void buildContactData() {
        List<User> users = userDao.selectAll();

        for (User user : users) {
            Contract contract = new Contract();
            contract.setUserId(user.getId());
            contract.setConfirm(ConfirmStateEnum.NO_SIGN.getCode());
            contractDao.insert(contract);
        }

    }

}
