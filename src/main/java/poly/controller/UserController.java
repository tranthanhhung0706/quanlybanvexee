package poly.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ptithcm.Entity.Account;
import ptithcm.Entity.Nhan_Vien;
import ptithcm.Entity.User;
import ptithcm.Entity.Ve_Xe;


@Controller
@RequestMapping("site/")
@Transactional
public class UserController {
	@Autowired
	SessionFactory factory;
	static User usertemp;
	static Account accounttemp;
    @RequestMapping(value = "in_for/{userId}.htm",params = "btnid")
    public String infor(ModelMap model,@PathVariable("userId") Integer userId,@ModelAttribute("khach_hang") User khach_hang){
    	User list=this.getuser(userId);
    	usertemp=this.getuser(userId);
    	model.addAttribute("khach_hangs",list);
    	model.addAttribute("khach_hang",list);
    	System.out.println(list);
    	return "site/user/userInfo";
    
    }
    @RequestMapping(value = "update",params = "btnEdit")
    public String update(ModelMap model,@ModelAttribute("khach_hang") User khach_hang) {
    	try {
    		khach_hang.setPhoneNumber(usertemp.getPhoneNumber());
    		khach_hang.setDiaChi(usertemp.getDiaChi());
    		khach_hang.setIdTaiKhoan(usertemp.getIdTaiKhoan());
			Integer check=this.updatekh(khach_hang);
			if(check==1) {
				model.addAttribute("message","update thanh cong");
			}else {
				model.addAttribute("message","update that bai");
			}
			model.addAttribute("khach_hangs",this.getuser(khach_hang.getUserId()));
		} catch (Exception e) {
			// TODO: handle exception
			return "site/user/userInfo";
		}
    	return "site/user/userInfo";
    }
    @RequestMapping(value = "userbookedticket/{userId}.htm",params = "btnid1")
    public String userbook(ModelMap model,@PathVariable("userId") Integer userId) {
    	List<Ve_Xe> list=this.getve(userId);
    	System.out.println(list);
    	model.addAttribute("listve",list);
    	return "site/user/userBookedTickets";
    }
    @RequestMapping(value = "changepassword/{userId}.htm",params = "btnid2")
    public String change(ModelMap model,@PathVariable("userId") Integer userId,@ModelAttribute("tai_khoan") Account tai_khoan) {
//    	List<Ve_Xe> list=this.getve(userId);
//    	System.out.println(list);
//    	model.addAttribute("listve",list);
    	User list=this.getuser(userId);
    	accounttemp=this.getaccuser(list.getIdTaiKhoan().getAccountId());
    	return "site/user/changepassword";
    }
    @RequestMapping(value = "changepassword")
    public String changepassword(ModelMap model,@ModelAttribute("tai_khoan") Account tai_khoan) {
    
    	tai_khoan.setAccountId(accounttemp.getAccountId());
    	tai_khoan.setAccountState(accounttemp.getAccountState());
    	tai_khoan.setIdRole(accounttemp.getIdRole());
    	Integer check=this.updateacc(tai_khoan);
    	if(check==1) {
    		model.addAttribute("message","doi mat khau thanh cong");
    	}else {
    		model.addAttribute("message","doi mat khau that bai");
    	}
    	System.out.println(accounttemp);
    	return "site/user/changepassword";
    }
//    @RequestMapping(value = "changepassword",method = RequestMethod.POST)
//    public String changepassword1(ModelMap model,@ModelAttribute("tai_khoan") Account tai_khoan) {
//    	return "site/user/changepassword";
//    }
    @RequestMapping("timkiem")
    public String timkiem(ModelMap model,@RequestParam("maVeInput") Integer mave) {
    	Ve_Xe list=this.getve1(mave);
    	System.out.println(list);
    	model.addAttribute("listve1",list);
    	return "site/user/userBookedTickets";
    }
    
    public Account getaccuser(Integer idtk){
    	Session session=factory.getCurrentSession();
    	String hql="from Account where accountId =:idtk ";
    	Query query=session.createQuery(hql);
    	query.setParameter("idtk", idtk);
    	Account list=(Account) query.list().get(0);
    	return list;
    }
    
    public List<Ve_Xe> getve(Integer userid){
    	Session session=factory.getCurrentSession();
    	String hql="from Ve_Xe v where v.idKhachHang.userId =:userid";
    	Query query=session.createQuery(hql);
    	query.setParameter("userid", userid);
    	List<Ve_Xe> list=query.list();
    	return list;
    }
    public Ve_Xe getve1(Integer idve) {
    	Session session=factory.getCurrentSession();
    	String hql="from Ve_Xe where idVe =:idve";
    	Query query=session.createQuery(hql);
    	query.setParameter("idve", idve);
    	Ve_Xe list=(Ve_Xe) query.list().get(0);
    	return list;
    }
     public Integer updateacc(Account tk) {
    	 Session session=factory.openSession();
    	 Transaction t=session.beginTransaction();
    	 try {
			session.update(tk);
			t.commit();
			
		} catch (Exception e) {
			// TODO: handle exception
			t.rollback();
			return 0;
		}finally {
			session.close();
		}
    	 return 1;
     }
    public Integer updatekh(User user) {
    	Session session=factory.openSession();
    	Transaction t=session.beginTransaction();
    	try {
			session.update(user);
			t.commit();
		} catch (Exception e) {
			// TODO: handle exception
			t.rollback();
			return 0;
		}finally {
			session.close();
		}
    	return 1;
    }
    public User getuser(Integer userid) {
    	Session session=factory.getCurrentSession();
    	String hql="from User where userId =:userid";
    	Query query=session.createQuery(hql);
    	query.setParameter("userid", userid);
    	User list=(User) query.list().get(0);
    	return list;
    }
 

}
