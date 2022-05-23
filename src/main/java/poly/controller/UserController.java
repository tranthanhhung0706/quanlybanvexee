package poly.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import ptithcm.Entity.Account;
import ptithcm.Entity.Nhan_Vien;
import ptithcm.Entity.User;
import ptithcm.Entity.Ve_Xe;


@Controller
@RequestMapping("site/")
@Transactional
public class UserController {
	@Autowired
	ServletContext context;
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
//    	//Path path = new File(list.getHinhAnh().ge;
//    	String path = Path.
//    	try {
//			photo.transferTo(new ));
//		} catch (IllegalStateException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
    	String fullPath = list.getHinhAnh();
    	int index = fullPath.lastIndexOf("\\");
    	String fileName = fullPath.substring(index + 1);
    	
    	model.addAttribute("photo_name", fileName);
    	model.addAttribute("photo_name", fileName);
    	System.out.println(list);
    	return "site/user/userInfo";
    
    }
    @RequestMapping(value = "update",params = "btnEdit")
    public String update(ModelMap model, @Validated @ModelAttribute("khach_hang") User khach_hang, BindingResult errors, @RequestParam("hinhAnh2") MultipartFile photo ) {
    	
    	//,@RequestParam("hinhAnh") MultipartFile photo
    	if(khach_hang.getHoTen().trim().equals("") ) {
			errors.rejectValue("hoTen", "khach_hang", "Vui lòng nhập họ và tên !");
		}
    	
        
    	
    	if(khach_hang.getEmail().trim().equals("")) {
			errors.rejectValue("email", "khach_hang", "Vui lòng nhập email !");
		}
    	if(!checkMail(khach_hang.getEmail()) && !khach_hang.getEmail().trim().equals("")) {
			errors.rejectValue("email", "khach_hang", "Vui lòng nhập đúng định dạng email !");
		}
    	
    	
    	if(khach_hang.getCmnd().trim().equals("")) {
			errors.rejectValue("cmnd", "khach_hang", "Vui lòng nhập cmnd !");
		}
    	
    	if(khach_hang.getNgheNghiep().trim().equals("")) {
			errors.rejectValue("ngheNghiep", "khach_hang", "Vui lòng nhập nghề nghiệp !");
		}
    	
    	if(errors.hasErrors()) return "site/user/userInfo";
    	
    	try {
    		
    		String photoPath=context.getRealPath(photo.getOriginalFilename());
    		khach_hang.setHinhAnh(photoPath);
    		System.out.print("danhjvhvjhhhhhhhhhhhhhhhhhhh" + photoPath);
    		
    		khach_hang.setPhoneNumber(usertemp.getPhoneNumber());
    		khach_hang.setDiaChi(usertemp.getDiaChi());
    		khach_hang.setIdTaiKhoan(usertemp.getIdTaiKhoan());
			Integer check=this.updatekh(khach_hang);
			if(check==1) {
				model.addAttribute("message","update thanh cong");
			}else {
				model.addAttribute("message","update that bai");
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			return "site/user/userInfo";
		}
    	
    	User user =this.getuser(khach_hang.getUserId());
    	try {
			photo.transferTo(new File(user.getHinhAnh()));
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	model.addAttribute("photo_name", photo.getOriginalFilename());
    	
    	
    	model.addAttribute("khach_hangs",this.getuser(khach_hang.getUserId()));
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
    
    	//,@RequestParam("passwordOld") String passwordOld
    	//Account tk = this.gettk(String.valueOf(accounttemp.getAccountId()), passwordOld);
    	//if(tk==null) System.out.print("kiem tradddddddddd");
    	
    	
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
    
    public  boolean checkMail(String emailAddress) {
    	String regexPattern = "^(.+)@(\\S+)$";
        return Pattern.compile(regexPattern).matcher(emailAddress).matches();
    }
    
    public Account gettk(String username,String password) {
    	Session session=factory.getCurrentSession();
    	String hql="from Account where user_id =:username and password =:password";
    	Query query=session.createQuery(hql);
    	query.setParameter("user_id", username);
    	query.setParameter("password", password);
    	Account list=(Account) query.list().get(0);
    	return list;
    }
    
    public Integer updatetk(Account tk) {
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
 

}
