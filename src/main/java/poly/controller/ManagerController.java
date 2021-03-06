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

        // L???y list danh s??ch nh??n vi??n
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
        // Kh??ng ngh??? vi???c v??o role kh??ng ph???i l?? admin
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
        // X??? l?? th??m ph???n account, v???i l???i ????nh d???u x??a ch??? kh??ng x??a nv t???i v?? r??ng
        // bu???c kh??a ngoai n???u nh??n vi??n ???? ???? t???ng thanh to??n v??,
        // n???u kh??ng c?? thanh to??n v?? n??o th?? x??a b??nh th?????ng
        if (!deleteNV.getVeXeList().isEmpty()) {
            deleteNV.setDaNghiViec(1);
            // L???y account ????? v?? hi???u h??a
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
        // Ki???m tra t???n t???i c???a ?????a ??i???m ??i v?? ?????n
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
        // Ki???m tra s??? tr??ng tuy???n xe
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
        // Ch??? ???????c x??a khi kh??ng c?? chuy???n xe n??o ch???y tuy???n n??y
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
        // -N???u tuy???n xe kh??ng c??, ho???c c?? chuy???n xe (ch??a c?? ai ?????t v??, ho???c to??n b??? v??
        // ???? b??? h???y)
        // th?? s???a b??nh th?????ng
        Tuyen_Xe tuyenXe = this.getTuyenXe(idTuyenXe);
        // -- Tuy???n xe kh??ng c?? chuy???n xe n??o
        if (tuyenXe.getChuyenXeList().isEmpty()) {
            model.addAttribute("tuyenXe", tuyenXe);
            System.err.println(tuyenXe);
            model.addAttribute("isUpdateTuyenXe", true);
            model.addAttribute("diPlaces", this.tatCaDiaDiem());
            model.addAttribute("denPlaces", this.tatCaDiaDiem());
            return "site/manager/manager";
        } else { // -- Tuy???n xe c?? chuy???n xe
            /*
             * L???p qua t???ng chuy???n xe xem c?? ai ?????t v?? ch??a b???ng c??ch l???p qua danh s??ch c??c
             * v?? xe c???a m???i chuy???n xe. +N???u c??: th?? xem t???t c??? v?? ???? c?? ??ang b??? h???y kh??ng
             * -T???t c??? v?? b??? h???y -> update ???????c -C?? 1 v?? ch??? thanh to??n ho???c ???? thanh to??n
             * -> kh??ng update ???????c +N???u kh??ng: update b??nh th?????ng
             */
            // + N???u c??
            for (Chuyen_Xe chuyenXe : tuyenXe.getChuyenXeList()) {
                for (Ve_Xe veXe : chuyenXe.getVeXeList()) {
                    if (veXe.getTrangThai().equals("Ch??? thanh to??n") || veXe.getTrangThai().equals("???? thanh to??n")) {
                        model.addAttribute("daBiThanhToan", true);
                        return "redirect:/quanly/.htm?isQuanLyTuyenXe=true";
                    }
                }
            }
            // + N???u kh??ng
            model.addAttribute("tuyenXe", tuyenXe);
            System.err.println(tuyenXe);
            model.addAttribute("isUpdateTuyenXe", true);
            return "site/manager/manager";
        }

        // -N???u tuy???n xe c?? chuy???n xe m?? ???? c?? v?? ?????t
        // (ch??? thanh to??n ho???c ???? thanh to??n th?? kh??ng th??? update) tuy???n xe

    }

    @RequestMapping(value = "themDiaDiem",method = RequestMethod.POST)
    public String themDiaDiem(@ModelAttribute("diaDiem") Dia_Diem diaDiem, ModelMap model) {
        // Ki???m tra ?????a ??i???m t???n t???i
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

        // -N???u ?????a ??i???m ???? ch??a g???n trong tuy???n xe n??o

        this.delete(idDiaDiem);
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
    
    public void delete(int idDiaDiem) {
        // TODO Auto-generated method stub
    	
    	Session currentSession = sessionFactory.openSession();
        Transaction t=currentSession.beginTransaction();
        Dia_Diem temp = (Dia_Diem) currentSession.get(Dia_Diem.class, idDiaDiem);
        currentSession.delete(temp);
        t.commit();
       currentSession.close();
    }

    @RequestMapping(value = "xuLyDiaDiem", params = "updateDiaDiemBtn", method = RequestMethod.POST)
    public String updateDiaDiem(@RequestParam("idDiaDiem") int idDiaDiem, ModelMap model) {
        // -Tr?????ng h???p update ???????c:
        // 1. ?????a ??i???m ch??a g???n trong tuy???n xe n??o
        // 2. ???? g???n v??o tuy???n xe nh??ng
        // - C?? chuy???n xe: t???t c??? c??c v?? ph???i l?? b??? h???y, ho???c l?? chuy???n xe kh??ng c?? v??
        // n??o
        // -Kh??ng c?? chuy???n xe
        Dia_Diem diaDiem = this.getDiaDiem(idDiaDiem);
        // 1. ?????a ??i???m ch??a g???n trong tuy???n xe n??o
        if (diaDiem.getTuyenXeDiList().isEmpty() && diaDiem.getTuyenXeDenList().isEmpty()) {

        } else { // 2. ???? g???n v??o tuy???n xe, th?? l???p qua t???ng chuy???n xe trong tuy???n coi ???? c?? v??
                 // ch??a
            List<Tuyen_Xe> tuyenXeDiList = diaDiem.getTuyenXeDiList();
            List<Tuyen_Xe> tuyenXeDenList = diaDiem.getTuyenXeDenList();
            // -C?? chuy???n xe
            // N???u chuy???n xe ??i not empty
            if (!tuyenXeDiList.isEmpty()) {
                for (Tuyen_Xe tuyenXe : tuyenXeDiList) {
                    // coi tuy???n c?? chuy???n n??o kh??ng, n???u c?? th?? l???p cho t???ng chuy???n xe c???a tuy???n ????
                    // ????? ki???m tra ??i???u ki???n v??
                    if (!tuyenXe.getChuyenXeList().isEmpty()) {
                        for (Chuyen_Xe chuyenXe : tuyenXe.getChuyenXeList()) {
                            // N???u chuy???n c?? v??
                            if (!chuyenXe.getVeXeList().isEmpty()) {

                                for (Ve_Xe veXe : chuyenXe.getVeXeList()) {
                                    // kh??ng b??? h???y th?? kh??ng update ???????c
                                    if (!veXe.getTrangThai().equals("???? h???y")) {
                                        model.addAttribute("tonTaiVe", true);
                                        return "redirect:/quanly/.htm?isQuanLyDiaDiem=true";
                                    }
                                }
                                // Th???a kh??ng c?? v?? n??o ho???c t???t c??? v?? ???? h???y c???a chuy???n xe n??y.
                            }
                        }
                        // Th???a kh??ng c?? v?? n??o ho???c t???t c??? v?? ???? h???y c???a t???t c??? chuy???n xe c???a tuy???n xe
                        // n??y
                    }

                }
            }

            if (!tuyenXeDenList.isEmpty()) {
                for (Tuyen_Xe tuyenXe : tuyenXeDenList) {
                    // coi tuy???n c?? chuy???n n??o kh??ng, n???u c?? th?? l???p cho t???ng chuy???n xe c???a tuy???n ????
                    // ????? ki???m tra ??i???u ki???n v??
                    if (!tuyenXe.getChuyenXeList().isEmpty()) {
                        for (Chuyen_Xe chuyenXe : tuyenXe.getChuyenXeList()) {
                            // N???u chuy???n c?? v??
                            if (!chuyenXe.getVeXeList().isEmpty()) {

                                for (Ve_Xe veXe : chuyenXe.getVeXeList()) {
                                    // kh??ng b??? h???y th?? kh??ng update ???????c
                                    if (!veXe.getTrangThai().equals("???? h???y")) {
                                        model.addAttribute("tonTaiVe", true);
                                        return "redirect:/quanly/.htm?isQuanLyDiaDiem=true";
                                    }
                                }
                                // Th???a kh??ng c?? v?? n??o ho???c t???t c??? v?? ???? h???y c???a chuy???n xe n??y.
                            }
                        }
                        // Th???a kh??ng c?? v?? n??o ho???c t???t c??? v?? ???? h???y c???a t???t c??? chuy???n xe c???a tuy???n xe
                        // n??y
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

