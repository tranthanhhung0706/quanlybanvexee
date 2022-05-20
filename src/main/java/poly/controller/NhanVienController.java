package poly.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.eclipse.jdt.internal.compiler.ast.ThisReference;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.annotations.Parent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ptithcm.Entity.Nhan_Vien;
import ptithcm.Entity.Ve_Xe;

@Controller
@RequestMapping("site/")
@Transactional
public class NhanVienController {
   @Autowired
   SessionFactory factory;
   static Nhan_Vien nhanvientemp;
   @RequestMapping(value = "in_for2/{idNhanVien}.htm",params = "btnid2")
   public String infornv(ModelMap model,@PathVariable("idNhanVien") Integer idNhanVien,@ModelAttribute("nhan_vien") Nhan_Vien nhan_vien ) {
	   Nhan_Vien list=this.getnv(idNhanVien);
	   nhanvientemp=this.getnv(idNhanVien);
	   model.addAttribute("nhan_viens",list);
	   model.addAttribute("nhan_vien",this.getnv(idNhanVien));
	   return "site/user/userInfo";
   }
   @RequestMapping(value = "update2",params = "btnEdit2")
   public String update2(ModelMap model,@ModelAttribute("nhan_vien") Nhan_Vien nhan_vien) {
	   try {
		   nhan_vien.setIdTaiKhoan(nhanvientemp.getIdTaiKhoan());
		   nhan_vien.setDaNghiViec(nhanvientemp.getDaNghiViec());
		   Integer check=this.updatenv(nhan_vien);
		   if(check==1) {
			   model.addAttribute("messsage","update thanh cong");
		   }else {
			   model.addAttribute("message","update that bai");
		   }
		   model.addAttribute("nhan_viens",this.getnv(nhan_vien.getIdNhanVien()));
	} catch (Exception e) {
		// TODO: handle exception
		return "site/user/userInfo";
	}
	   return "site/user/userInfo";
   }
   @RequestMapping(value = "thanh_toan/{idNhanVien}.htm",params = "btnid3")
   public String thanhtoan(ModelMap model,@PathVariable("idNhanVien") Integer idNhanVien) {
	   
	   List<Ve_Xe> list=this.getves();
	   System.out.println(list);
	   model.addAttribute("trang_thai_ve",list);
	   return "site/nhanvien/veChuaThanhToanList";
   }
   @RequestMapping("timkiem2")
   public String timkiem(ModelMap model,@RequestParam("maVeInput") Integer mave,HttpServletRequest request) {
	   Ve_Xe list1=this.getve1(mave);
	   model.addAttribute("listve1",list1);
	   System.out.println(request.getParameter("tuyenDuongInput"));
	   return "site/nhanvien/veChuaThanhToanList";
   }
   public Ve_Xe getve1(Integer idve) {
	   Session session=factory.getCurrentSession();
	   String hql="from Ve_Xe where idVe =:idve";
	   Query query=session.createQuery(hql);
	   query.setParameter("idve", idve);
	   Ve_Xe list=(Ve_Xe) query.list().get(0);
	   return list;
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
   public Integer updatenv(Nhan_Vien nv) {
	   Session session=factory.openSession();
	   Transaction t=session.beginTransaction();
	   try {
		   session.update(nv);
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
   public Nhan_Vien getnv(Integer idnv) {
	   Session session=factory.getCurrentSession();
	   String hql="from Nhan_Vien where idNhanVien=:idnv ";
	   Query query=session.createQuery(hql);
	   query.setParameter("idnv", idnv);
	   Nhan_Vien list=(Nhan_Vien) query.list().get(0);
	   return list;
   }
}
