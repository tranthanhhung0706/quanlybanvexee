package poly.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import net.sf.ehcache.hibernate.HibernateUtil;
import ptithcm.Entity.Account;
import ptithcm.Entity.Chuyen_Xe;
import ptithcm.Entity.Dia_Diem;

import ptithcm.Entity.Nhan_Vien;
import ptithcm.Entity.Role;
import ptithcm.Entity.Tuyen_Xe;
import ptithcm.Entity.Ve_Xe;

@Controller
@RequestMapping("/quanly/")
@Transactional
public class ManagerController {
	@Autowired
	SessionFactory sessionFactory;
	 @RequestMapping("logout")
	 	public String logout(HttpSession ss) {
	 	
	 		ss.removeAttribute("tk_ad");
	 		ss.removeAttribute("tai_khoans");
	 		return "redirect:site/index.htm";
	 	}
	 @RequestMapping("site/index")
     public String index(ModelMap model,HttpSession session) {
    	 List<Dia_Diem> list=this.getdiadiem();
    	 session.setAttribute("tatCaDiaDiem", list);
    	 return "site/index";
     }
	   public List<Dia_Diem> getdiadiem(){
	    	 Session session=sessionFactory.getCurrentSession();
	    	 String hql="from Dia_Diem ";
	    	 Query query=session.createQuery(hql);
	    	 List<Dia_Diem> list=query.list();
	    	 return list;
	     }
    @RequestMapping("/.htm")
    public String managerIndex(ModelMap model, @RequestParam(value = "isShowList", required = false) boolean isShowList,
            @RequestParam(value  = "isThemNV", required = false) boolean isThemNV,
            @RequestParam(value  = "isQuanLyTuyenXe", required = false) boolean isQuanLyTuyenXe,
            @RequestParam(value = "isThemTuyenXe", required = false) boolean isThemTuyenXe,
            @RequestParam(value  = "isQuanLyDiaDiem", required = false) boolean isQuanLyDiaDiem,
            @RequestParam(value = "isThemDiaDiem", required = false) boolean isThemDiaDiem) {
        System.out.println(isShowList);

        // Lấy list danh sách nhân viên
        if (isShowList) {
            List<Nhan_Vien> nhanVienList =this.getListNV();
            model.addAttribute("nvList", nhanVienList);
        }
        if (isThemNV) {
            model.addAttribute("nhanVien", new Nhan_Vien());
        }
        if (isQuanLyTuyenXe) {
            List<Tuyen_Xe> tuyenXeList = this.getTuyenXeList();
            model.addAttribute("tuyenXeList", tuyenXeList);
        }
        if (isThemTuyenXe) {
            model.addAttribute("tuyenXe", new Tuyen_Xe());
            model.addAttribute("places", this.tatCaDiaDiem());
        }
        if (isQuanLyDiaDiem) {
            model.addAttribute("places", this.tatCaDiaDiem());
        }
        if (isThemDiaDiem) {
            model.addAttribute("diaDiem", new Dia_Diem());
        }

        return "site/manager/manager";
    }
    
 
    public List<Nhan_Vien> getListNV() {
        // TODO Auto-generated method stub
        Session currentSession = sessionFactory.getCurrentSession();
        // Không nghỉ việc vào role không phải là admin
        String hSQL = "FROM Nhan_Vien NV WHERE NV.daNghiViec=0 AND NOT NV.idTaiKhoan.idRole.authority='ROLE_MANAGER'";
        Query query = currentSession.createQuery(hSQL);
        List<Nhan_Vien> nvList = query.list();
        return nvList;
    }
    
    
    public List<Tuyen_Xe> getTuyenXeList() {
        // TODO Auto-generated method stub
        Session currentSession = sessionFactory.getCurrentSession();
        String sHQL = "FROM Tuyen_Xe";
        Query query = currentSession.createQuery(sHQL);
        List<Tuyen_Xe> tuyenXeList = query.list();
        return tuyenXeList;
    }
    
    public List<Dia_Diem> tatCaDiaDiem() {
        Session currentSession = sessionFactory.getCurrentSession();
        String sHQL = "FROM Dia_Diem";
        Query query = currentSession.createQuery(sHQL);
        List<Dia_Diem> diaDiemList = query.list();
        return diaDiemList;
    }

    @RequestMapping(value = "/xoaNV",method = RequestMethod.POST)
    public String xoaNVPOST(@RequestParam("idNV") int nvID) {
        this.xoaNV(nvID);
        return "redirect:/quanly/.htm?isShowList=true";
    }
    
    public void xoaNV(int nvID) {
        // TODO Auto-generated method stub
        Session currentSession = sessionFactory.openSession();
        Transaction t=currentSession.beginTransaction();
        Nhan_Vien deleteNV = (Nhan_Vien) currentSession.get(Nhan_Vien.class, nvID);
        // Xử lý thêm phần account, với lại đánh dấu xóa chứ không xóa nv tại vì ràng
        // buộc khóa ngoai nếu nhân viên đó đã từng thanh toán vé,
        // nếu không có thanh toán vé nào thì xóa bình thường
        if (!deleteNV.getVeXeList().isEmpty()) {
            deleteNV.setDaNghiViec(1);
            // Lấy account để vô hiệu hóa
            deleteNV.getIdTaiKhoan().setAccountState(0);
            ;
            currentSession.update(deleteNV);
        } else {
            currentSession.delete(deleteNV.getIdTaiKhoan().getIdRole());
            currentSession.delete(deleteNV.getIdTaiKhoan());
            currentSession.delete(deleteNV);
        }
        t.commit();
        currentSession.close();

    }

    @RequestMapping(value = "/themNV",method = RequestMethod.POST)
    public String themNV(@ModelAttribute("nhanVien") Nhan_Vien nhanVien, @RequestParam("dd") String dd,
            @RequestParam("mm") String mm, @RequestParam("yyyy") String yyyy, @RequestParam("bdlv-dd") String bdlv_dd,
            @RequestParam("bdlv-mm") String bdlv_mm, @RequestParam("bdlv-yyyy") String bdlv_yyyy,
            @RequestParam("password") String password, ModelMap model) {
        Account tempAccount = this.getAccountFromUsername(nhanVien.getSoDienThoai());
        if (tempAccount != null) {
            model.addAttribute("tontaiSDT", true);
            return "redirect:/quanly/.htm?isThemNV=true";
        }
        if (mm.length() == 1)
            mm = "0".concat(mm);
        if (bdlv_mm.length() == 1)
            bdlv_mm = "0".concat(bdlv_mm);
        nhanVien.setNgaySinh(yyyy + "-" + mm + "-" + dd);
        nhanVien.setNgayBDLamViec(bdlv_yyyy + "-" + bdlv_mm + "-" + bdlv_dd);
        Account newAccount = new Account(nhanVien.getSoDienThoai(),password);
        
        Role nvRole = new Role("ROLE_EMPLOYEE");
        newAccount.addRole(nvRole);
        nhanVien.setIdTaiKhoan(newAccount);
        this.themNV(nhanVien);

        return "redirect:/quanly/.htm?isShowList=true";
    }
    
    public void themNV(Nhan_Vien nhanVien) {
        // TODO Auto-generated method stub
    
        Session currentSession = sessionFactory.openSession();
        Transaction t=currentSession.beginTransaction();
        currentSession.save(nhanVien);
        t.commit();
        currentSession.close();
    }
    
    public Account getAccountFromUsername(String username) {
		Session currentSession = sessionFactory.getCurrentSession();
		String sHQL = "FROM Account A WHERE A.username = :username";
		Query query = currentSession.createQuery(sHQL);
		query.setParameter("username", username);
		List<Account> accountList = query.list();
		if (accountList.isEmpty())
			return null;
		return accountList.get(0);
	}

    @RequestMapping(value = "xuLyTuyenXe", params = "add", method = RequestMethod.POST)
    public String themTuyenXe(@ModelAttribute("tuyenXe") Tuyen_Xe tuyenXe, ModelMap model) {
        System.err.println(tuyenXe);
        // Kiểm tra tồn tại của địa điểm đi và đến
        boolean tonTaiDiemDi = false;
        boolean tonTaiDiemDen = false;
        Dia_Diem diaDiemDi = null;
        Dia_Diem diaDiemDen = null;
        List<Dia_Diem> diaDiemList = this.tatCaDiaDiem();
        for (Dia_Diem diaDiem : diaDiemList) {
            if (diaDiem.getTenDiaDiem().equals(tuyenXe.getDiaDiemDi().getTenDiaDiem())) {
                tonTaiDiemDi = true;
                diaDiemDi = diaDiem;

            }
            if (diaDiem.getTenDiaDiem().equals(tuyenXe.getDiaDiemDen().getTenDiaDiem())) {
                tonTaiDiemDen = true;
                diaDiemDen = diaDiem;
            }
        }
        if (!tonTaiDiemDi || !tonTaiDiemDen) {
            model.addAttribute("diaDiemKhongTonTai", true);
            return "redirect:/quanly/.htm?isThemTuyenXe=true";
        }
        Tuyen_Xe tempTuyenXe = this.getTuyenXe(diaDiemDi.getIdDiaDiem(), diaDiemDen.getIdDiaDiem());
        if (tempTuyenXe != null) {
            model.addAttribute("tonTaiTuyenXe", true);
            System.err.println(tempTuyenXe);
            return "redirect:/quanly/.htm?isThemTuyenXe=true";
        }
        tuyenXe.setDiaDiemDi(diaDiemDi);
        tuyenXe.setDiaDiemDen(diaDiemDen);
        this.save(tuyenXe);
        return "redirect:/quanly/.htm?isQuanLyTuyenXe=true";
    }
    
    public void save(Tuyen_Xe tuyenXe) {
        // TODO Auto-generated method stub
        Session currentSession = sessionFactory.openSession();
        Transaction t=currentSession.beginTransaction();
        currentSession.save(tuyenXe);
         t.commit();
        currentSession.close();
    }
    
    public Tuyen_Xe getTuyenXe(int idDiemDi, int idDiemDen) {
        Session currentSession = sessionFactory.getCurrentSession();
        String sHQL = "FROM Tuyen_Xe TX WHERE TX.diaDiemDi.idDiaDiem = :idDiemDi"
                + " AND TX.diaDiemDen.idDiaDiem = :idDiemDen";
        Query query = currentSession.createQuery(sHQL);
        query.setParameter("idDiemDi", idDiemDi);
        query.setParameter("idDiemDen", idDiemDen);

        List<Tuyen_Xe> tuyenXeList = query.list();
        if (tuyenXeList.isEmpty())
            return null;
        return tuyenXeList.get(0);
    }

    @RequestMapping(value = "xuLyTuyenXe", params = "update", method = RequestMethod.POST)
    public String updateTuyenXe(@ModelAttribute("tuyenXe") Tuyen_Xe tuyenXe, @RequestParam("idTuyenXe") int idTuyenXe,
            @RequestParam("diaDiemDiCu") String diaDiemDiCu, @RequestParam("diaDiemDenCu") String diaDiemDenCu,
            ModelMap model) {
        // Kiểm tra sự trùng tuyến xe
        List<Tuyen_Xe> tuyenXeList = this.getTuyenXeList();
        for (int i = 0; i < tuyenXeList.size(); i++) {
            if (tuyenXeList.get(i).getDiaDiemDi().getTenDiaDiem().equals(diaDiemDiCu)
                    && tuyenXeList.get(i).getDiaDiemDen().getTenDiaDiem().equals(diaDiemDenCu)) {
                tuyenXeList.remove(i);
                break;
            }
        }
        for (Tuyen_Xe temp : tuyenXeList) {
            if (temp.getDiaDiemDi().getTenDiaDiem().equals(tuyenXe.getDiaDiemDi().getTenDiaDiem())
                    && temp.getDiaDiemDen().getTenDiaDiem().equals(tuyenXe.getDiaDiemDen().getTenDiaDiem())) {
                model.addAttribute("isUpdateTuyenXe", true);
                model.addAttribute("tonTaiTuyenXe", true);
                return "site/manager/manager";
            }
        }
        tuyenXe.setIdTuyen(idTuyenXe);
        List<Dia_Diem> places = this.tatCaDiaDiem();
        Dia_Diem diaDiemDi = null;
        Dia_Diem diaDiemDen = null;

        for (Dia_Diem place : places) {
            if (place.getTenDiaDiem().equals(tuyenXe.getDiaDiemDi().getTenDiaDiem())) {
                diaDiemDi = place;
            }
            if (place.getTenDiaDiem().equals(tuyenXe.getDiaDiemDen().getTenDiaDiem())) {
                diaDiemDen = place;
            }
        }

        System.err.println(diaDiemDi);
        System.err.println(diaDiemDen);
        tuyenXe.setDiaDiemDi(diaDiemDi);
        tuyenXe.setDiaDiemDen(diaDiemDen);
        System.err.println(tuyenXe);

        this.update(tuyenXe);
        return "redirect:/quanly/.htm?isQuanLyTuyenXe=true";
    }
    
    public void update(Tuyen_Xe tuyenXe) {
        // TODO Auto-generated method stub
        Session currentSession = sessionFactory.openSession();
        Transaction t=currentSession.beginTransaction();
        currentSession.update(tuyenXe);
        t.commit();
        currentSession.close();
    }

    @RequestMapping(value = "xuLyTuyenXe", params = "deleteBtn", method = RequestMethod.POST)
    public String deleteTuyenXe(@RequestParam("idTuyenXe") int idTuyenXe, ModelMap model) {
        // Chỉ được xóa khi không có chuyến xe nào chạy tuyến này
        Tuyen_Xe tuyenXe = this.getTuyenXe(idTuyenXe);
        if (tuyenXe.getChuyenXeList().size() > 0) {
            model.addAttribute("tonTaiChuyenXe", true);
            return "redirect:/quanly/.htm?isQuanLyTuyenXe=true";
        }
        this.deleteTuyenXe(idTuyenXe);
        model.addAttribute("xoaTuyenXeThanhCong", true);
        return "redirect:/quanly/.htm?isQuanLyTuyenXe=true";
    }
    
    public void deleteTuyenXe(int idTuyenXe) {
        // TODO Auto-generated method stub
        Session currentSession = sessionFactory.openSession();
        Transaction t=currentSession.beginTransaction();
        Tuyen_Xe tuyenXe = (Tuyen_Xe) currentSession.get(Tuyen_Xe.class, idTuyenXe);
        currentSession.delete(tuyenXe);
        t.commit();
        currentSession.close();
    }
    
    public Tuyen_Xe getTuyenXe(int idTuyenXe) {
        // TODO Auto-generated method stub
        Session currentSession = sessionFactory.getCurrentSession();
        return (Tuyen_Xe) currentSession.get(Tuyen_Xe.class, idTuyenXe);

    }

    @RequestMapping(value = "xuLyTuyenXe", params = "updateBtn", method = RequestMethod.POST)
    public String updateTuyenXe(@RequestParam("idTuyenXe") int idTuyenXe, ModelMap model) {
        // -Nếu tuyến xe không có, hoặc có chuyến xe (chưa có ai đặt vé, hoặc toàn bộ vé
        // đã bị hủy)
        // thì sửa bình thường
        Tuyen_Xe tuyenXe = this.getTuyenXe(idTuyenXe);
        // -- Tuyến xe không có chuyến xe nào
        if (tuyenXe.getChuyenXeList().isEmpty()) {
            model.addAttribute("tuyenXe", tuyenXe);
            System.err.println(tuyenXe);
            model.addAttribute("isUpdateTuyenXe", true);
            model.addAttribute("diPlaces", this.tatCaDiaDiem());
            model.addAttribute("denPlaces", this.tatCaDiaDiem());
            return "site/manager/manager";
        } else { // -- Tuyến xe có chuyến xe
            /*
             * Lặp qua từng chuyến xe xem có ai đặt vé chưa bằng cách lặp qua danh sách các
             * vé xe của mỗi chuyến xe. +Nếu có: thì xem tất cả vé đó có đang bị hủy không
             * -Tất cả vé bị hủy -> update được -Có 1 vé chờ thanh toán hoặc đã thanh toán
             * -> không update được +Nếu không: update bình thường
             */
            // + Nếu có
            for (Chuyen_Xe chuyenXe : tuyenXe.getChuyenXeList()) {
                for (Ve_Xe veXe : chuyenXe.getVeXeList()) {
                    if (veXe.getTrangThai().equals("Chờ thanh toán") || veXe.getTrangThai().equals("Đã thanh toán")) {
                        model.addAttribute("daBiThanhToan", true);
                        return "redirect:/quanly/.htm?isQuanLyTuyenXe=true";
                    }
                }
            }
            // + Nếu không
            model.addAttribute("tuyenXe", tuyenXe);
            System.err.println(tuyenXe);
            model.addAttribute("isUpdateTuyenXe", true);
            return "site/manager/manager";
        }

        // -Nếu tuyến xe có chuyến xe mà đã có vé đặt
        // (chờ thanh toán hoặc đã thanh toán thì không thể update) tuyến xe

    }

    @RequestMapping(value = "themDiaDiem",method = RequestMethod.POST)
    public String themDiaDiem(@ModelAttribute("diaDiem") Dia_Diem diaDiem, ModelMap model) {
        // Kiểm tra địa điểm tồn tại
        List<Dia_Diem> places = this.tatCaDiaDiem();
        for (Dia_Diem temp : places) {
            if (temp.getTenDiaDiem().equals(diaDiem.getTenDiaDiem())) {
                model.addAttribute("tonTaiDiaDiem", true);
                return "redirect:/quanly/.htm?isThemDiaDiem=true";
            }
        }
        this.save(diaDiem);
        model.addAttribute("themDiaDiemThanhCong", true);
        return "redirect:/quanly/.htm?isThemDiaDiem=true";
    }
    
    public void save(Dia_Diem diaDiem) {
        // TODO Auto-generated method stub
    	Session currentSession = sessionFactory.openSession();
        Transaction t=currentSession.beginTransaction();
        currentSession.save(diaDiem);
        t.commit();
        currentSession.close();
    }

    @RequestMapping(value = "xuLyDiaDiem", params = "deleteDiaDiemBtn", method = RequestMethod.POST)
    public String deleteDiaDiem(@RequestParam("idDiaDiem") int idDiaDiem, ModelMap model) {

        // -Nếu địa điểm đó chưa gắn trong tuyến xe nào
        Dia_Diem diaDiem = this.getDiaDiem(idDiaDiem);

        if (!diaDiem.getTuyenXeDiList().isEmpty() || !diaDiem.getTuyenXeDenList().isEmpty()) {
            model.addAttribute("diaDiemTonTaiTuyen", true);
            return "redirect:/quanly/.htm?isQuanLyDiaDiem=true";
        }
        this.delete(diaDiem);
        model.addAttribute("xoaDiaDiem", true);
        return "redirect:/quanly/.htm?isQuanLyDiaDiem=true";
    }
    
    public Dia_Diem getDiaDiem(int idDiaDiem) {
        // TODO Auto-generated method stub
        Session currentSession = sessionFactory.getCurrentSession();
        Dia_Diem temp = (Dia_Diem) currentSession.get(Dia_Diem.class, idDiaDiem);
        Hibernate.initialize(temp.getTuyenXeDiList());
        Hibernate.initialize(temp.getTuyenXeDenList());
        return temp;
    }
    
    public void delete(Dia_Diem diaDiem) {
        // TODO Auto-generated method stub
    	
    	Session currentSession = sessionFactory.openSession();
        Transaction t=currentSession.beginTransaction();
        currentSession.delete(diaDiem);
        t.commit();
       currentSession.close();
    }

    @RequestMapping(value = "xuLyDiaDiem", params = "updateDiaDiemBtn", method = RequestMethod.POST)
    public String updateDiaDiem(@RequestParam("idDiaDiem") int idDiaDiem, ModelMap model) {
        // -Trường hợp update được:
        // 1. Địa điểm chưa gắn trong tuyến xe nào
        // 2. Đã gắn vào tuyến xe nhưng
        // - Có chuyến xe: tất cả các vé phải là bị hủy, hoặc là chuyến xe không có vé
        // nào
        // -Không có chuyến xe
        Dia_Diem diaDiem = this.getDiaDiem(idDiaDiem);
        // 1. Địa điểm chưa gắn trong tuyến xe nào
        if (diaDiem.getTuyenXeDiList().isEmpty() && diaDiem.getTuyenXeDenList().isEmpty()) {

        } else { // 2. Đã gắn vào tuyến xe, thì lập qua từng chuyến xe trong tuyến coi đã có vé
                 // chưa
            List<Tuyen_Xe> tuyenXeDiList = diaDiem.getTuyenXeDiList();
            List<Tuyen_Xe> tuyenXeDenList = diaDiem.getTuyenXeDenList();
            // -Có chuyến xe
            // Nếu chuyến xe đi not empty
            if (!tuyenXeDiList.isEmpty()) {
                for (Tuyen_Xe tuyenXe : tuyenXeDiList) {
                    // coi tuyến có chuyến nào không, nếu có thì lặp cho từng chuyến xe của tuyến đó
                    // để kiểm tra điều kiện vé
                    if (!tuyenXe.getChuyenXeList().isEmpty()) {
                        for (Chuyen_Xe chuyenXe : tuyenXe.getChuyenXeList()) {
                            // Nếu chuyến có vé
                            if (!chuyenXe.getVeXeList().isEmpty()) {

                                for (Ve_Xe veXe : chuyenXe.getVeXeList()) {
                                    // không bị hủy thì không update được
                                    if (!veXe.getTrangThai().equals("Đã hủy")) {
                                        model.addAttribute("tonTaiVe", true);
                                        return "redirect:/quanly/.htm?isQuanLyDiaDiem=true";
                                    }
                                }
                                // Thỏa không có vé nào hoặc tất cả vé đã hủy của chuyến xe này.
                            }
                        }
                        // Thỏa không có vé nào hoặc tất cả vé đã hủy của tất cả chuyến xe của tuyến xe
                        // này
                    }

                }
            }

            if (!tuyenXeDenList.isEmpty()) {
                for (Tuyen_Xe tuyenXe : tuyenXeDenList) {
                    // coi tuyến có chuyến nào không, nếu có thì lặp cho từng chuyến xe của tuyến đó
                    // để kiểm tra điều kiện vé
                    if (!tuyenXe.getChuyenXeList().isEmpty()) {
                        for (Chuyen_Xe chuyenXe : tuyenXe.getChuyenXeList()) {
                            // Nếu chuyến có vé
                            if (!chuyenXe.getVeXeList().isEmpty()) {

                                for (Ve_Xe veXe : chuyenXe.getVeXeList()) {
                                    // không bị hủy thì không update được
                                    if (!veXe.getTrangThai().equals("Đã hủy")) {
                                        model.addAttribute("tonTaiVe", true);
                                        return "redirect:/quanly/.htm?isQuanLyDiaDiem=true";
                                    }
                                }
                                // Thỏa không có vé nào hoặc tất cả vé đã hủy của chuyến xe này.
                            }
                        }
                        // Thỏa không có vé nào hoặc tất cả vé đã hủy của tất cả chuyến xe của tuyến xe
                        // này
                    }

                }
            }

        }
        model.addAttribute("DuDKChinhSuaDD", true);
        model.addAttribute("diaDiem", diaDiem);
        return "site/manager/manager";
    }

    @RequestMapping(value = "updateDiaDiem", method = RequestMethod.POST)
    public String updateDiaDiem(@ModelAttribute("diaDiem") Dia_Diem diaDiem) {
        System.err.println(diaDiem);
        this.update(diaDiem);
        return "redirect:/quanly/.htm?isQuanLyDiaDiem=true";
    }
    
    public void update(Dia_Diem diaDiem) {
        // TODO Auto-generated method stub
    	Session currentSession = sessionFactory.openSession();
        Transaction t=currentSession.beginTransaction();
        currentSession.update(diaDiem);
        t.commit();
        currentSession.close();
    }

}

