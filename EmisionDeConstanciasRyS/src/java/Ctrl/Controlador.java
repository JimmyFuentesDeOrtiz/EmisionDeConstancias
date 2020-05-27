/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ctrl;

import Config.Conexion;
import Entidad.Usuario;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author EquipoRUTS_ITSOEH
 */
@Controller
public class Controlador {

    Conexion con = new Conexion();
    JdbcTemplate jdbcTemplate = new JdbcTemplate(con.Conectar());
    ModelAndView mav = new ModelAndView();
    boolean tmp = false;
    String mat;
    List datos;

    @RequestMapping(value = "index.htm", method = RequestMethod.GET)
    public ModelAndView login() {
        tmp = false;
        mav.addObject(new Usuario());
        mav.setViewName("index");
        return mav;
    }

    @RequestMapping(value = "index.htm", method = RequestMethod.POST)
    public ModelAndView login(Usuario u) {
        String sql = "select * from alumnos where NoControl='" + u.getMatricula()
                + "' and Contraseña='" + u.getPass() + "'";
        mat = u.getMatricula();
        datos = this.jdbcTemplate.queryForList(sql);
        mav.addObject("lista", datos);
        tmp = false;
        if (datos.size() > 0) {
            tmp = true;
            mav.setViewName("inicio");
            if ("Master".equals(u.getMatricula())) {
                System.out.println(u.getMatricula());
                mav.setViewName("administrador");//cambiar a la de admin ****************
            }
        }
        return mav;
    }

    @RequestMapping("inicio.htm")
    public ModelAndView inicio() {
        if (datos.size() <= 0 || !tmp) {
            tmp = false;
            return new ModelAndView("redirect:/index.htm");
        }
        return mav;
    }

    @RequestMapping("menu_t_servicio.htm")
    public ModelAndView menu_t_servicio() {
        String sql = "select*from tramites where NoControl=" + mat + " and tipoTramite like '%ervicio%'";
        List datos = this.jdbcTemplate.queryForList(sql);
        int tamAr = datos.size() - 1;
        mav.addObject("listar", datos);
        mav.addObject("tamAr", tamAr);
        mav.setViewName("menu_t_servicio");
        return mav;
    }

    @RequestMapping("menu_t_residencia.htm")
    public ModelAndView menu_t_residencia() {
        String sql = "select*from tramites where NoControl=" + mat + " and tipoTramite like '%esidencia%'";
        List datos = this.jdbcTemplate.queryForList(sql);
        int tamAr = datos.size() - 1;
        mav.addObject("listar", datos);
        mav.addObject("tamAr", tamAr);
        mav.setViewName("menu_t_residencia");
        return mav;
    }
    
}

//"select * from alumnos where NoControl='" + u.getMatricula() + "' and Contraseña='" + u.getPass() + "'";
//"select*from tramites where NoControl=" + mat + " and tipoTramite like '%ervicio%'";
//"select*from tramites where NoControl=" + mat + " and tipoTramite like '%esidencia%'";