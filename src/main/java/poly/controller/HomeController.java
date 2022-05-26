package poly.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ptithcm.Entity.Dia_Diem;
import ptithcm.Entity.Nhan_Vien;
import ptithcm.Entity.Ve_Xe;

@Controller
@RequestMapping("site/")
@Transactional
public class HomeController {
	 @Autowired
	 SessionFactory factory;
	 @RequestMapping("index")
     public String index(ModelMap model,HttpSession session) {
    	 List<Dia_Diem> list=this.getdiadiem();
    	 session.setAttribute("tatCaDiaDiem", list);
    	 return "site/index";
     }
     public List<Dia_Diem> getdiadiem(){
    	 Session session=factory.getCurrentSession();
    	 String hql="from Dia_Diem ";
    	 Query query=session.createQuery(hql);
    	 List<Dia_Diem> list=query.list();
    	 return list;
     } 
     @RequestMapping("redirect")
 	public String logout(HttpSession ss) {
 		System.out.println("logout");
 		ss.removeAttribute("tk_nv");
 		ss.removeAttribute("tk_kh");
 		ss.removeAttribute("tai_khoans");
 		return "redirect:index.htm";
 	}
     @RequestMapping("chitiet/{idve}.htm")
     public String chitiet(ModelMap model,@PathVariable("idve") Integer idve) {
    	 model.addAttribute("veXe",this.getve(idve));
    	 return "site/chitietve";
     }
     public Ve_Xe getve(Integer idve){
    	 Session session=factory.getCurrentSession();
    	 String hql="From Ve_Xe where idVe =:idve";
    	 Query query=session.createQuery(hql);
    	 query.setParameter("idve", idve);
    	 Ve_Xe list =(Ve_Xe) query.list().get(0);
    	 return list;
     }
     @RequestMapping("huyve/{userId}.htm")
     public String huyve(@RequestParam("idVe") Integer idve,ModelMap model,@PathVariable("userId") Integer userId) {
    	 Ve_Xe list=this.getve(idve);
    	 list.setTrangThai("Đã hủy");
    	 model.addAttribute("listve",this.getve1(userId));
    	 return "site/user/userBookedTickets";
     }
     @RequestMapping("huyVe/{nvId}.htm")
     public String Huyve(@RequestParam("idVe") int idVe,ModelMap model,@PathVariable("nvId") Integer nvId) {
    	 Nhan_Vien tempnv=this.getnv(nvId);
    	 Ve_Xe list=this.getve(idVe);
    	 list.setTrangThai("Đã hủy");
    	 list.setIdNhanVien(tempnv);
    	 model.addAttribute("trang_thai_ve",this.getves());
    	 return "site/nhanvien/veChuaThanhToanList";
     }
     @RequestMapping("thanh_toan_ve/{nvId1}.htm")
     public String thanhtoanve(@RequestParam("idVe") int idVe,ModelMap model,@PathVariable("nvId1") Integer nvId1) {
    	 Nhan_Vien tempnv=this.getnv(nvId1);
    	 Ve_Xe list=this.getve(idVe);
    	 list.setTrangThai("Đã thanh toán");
    	 list.setIdNhanVien(tempnv);
    	 model.addAttribute("trang_thai_ve",this.getves());
    	 return "site/nhanvien/veChuaThanhToanList";
     }
     
     public List<Ve_Xe> getves(){
  	   Session session=factory.getCurrentSession();
  	   String hql="from Ve_Xe where trangThai =:trang_thai1 or trangThai =:trang_thai2";
  	   Query query=session.createQuery(hql);
  	   query.setParameter("trang_thai1", "Chờ thanh toán");
  	   query.setParameter("trang_thai2", "Đã hủy");	
  	   List<Ve_Xe> list=query.list();
  	   return list;
     }
     public Nhan_Vien getnv(Integer nvid) {
    	 Session session=factory.getCurrentSession();
    	 String hql="from Nhan_Vien where idNhanVien =:nvid";
    	 Query query=session.createQuery(hql);
    	 query.setParameter("nvid", nvid);
    	 Nhan_Vien list=(Nhan_Vien) query.list().get(0);
    	 return list;
     }
     
     public List<Ve_Xe> getve1(Integer userid){
    	 Session session=factory.getCurrentSession();
     	String hql="from Ve_Xe v where v.idKhachHang.userId =:userid";
     	Query query=session.createQuery(hql);
     	query.setParameter("userid", userid);
     	List<Ve_Xe> list=query.list();
     	return list;
     }
     
}
