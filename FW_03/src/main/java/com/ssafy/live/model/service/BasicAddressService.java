package com.ssafy.live.model.service;

import java.sql.Connection;
import java.sql.SQLException;

import org.springframework.stereotype.Service;

import com.ssafy.live.model.dao.AddressDao;
import com.ssafy.live.model.dao.BasicAddressDao;
import com.ssafy.live.model.dto.Address;
import com.ssafy.live.util.DBUtil;

import lombok.RequiredArgsConstructor;


//싱글톤 관련 코드 지우기 (1) + DBUtil, dao 의존성 = Has a 관계
//따라서 Autowired 해줘야함. 방법 세가지! 필드주입, 생성자주입 등등
//우리는 생성자 주입 할 것임. 롬복을 통해서! 반드시 필요한 의존성은 private final로 
@Service
@RequiredArgsConstructor
public class BasicAddressService implements AddressService {
	//객체 주입은 내가 하지 않는다. 스프링이 할 것임.
//    private AddressDao dao = BasicAddressDao.getDao();
//    private DBUtil util = DBUtil.getUtil();
	
	//@Required 어쩌구 추가하면서 얘네만 사용.
	private AddressDao dao;
    private DBUtil util;
    
	
	
	
  //싱글톤 관련 코드 지우기 (1)
//    private static BasicAddressService service = new BasicAddressService();
//
//    private BasicAddressService() {
//    }
//
//    public static BasicAddressService getService() {
//        return service;
//    }

    @Override
    public void registAddress(Address address) throws SQLException {
        Connection con = util.getConnection();
        try {
            con.setAutoCommit(false);
            dao.insert(con, address);
            con.commit();
        } catch (SQLException e) {
            con.rollback();
            throw e;
        } finally {
            util.close(con);
        }
    }

    @Override
    public void deleteAddress(int ano) throws SQLException {
        Connection con = util.getConnection();
        try {
            con.setAutoCommit(false);
            dao.delete(con, ano);
            con.commit();
        } catch (SQLException e) {
            con.rollback();
            throw e;
        } finally {
            util.close(con);
        }
    }

}
