<%-- 
    Document   : home
    Created on : 16/10/2015, 10:13:59 AM
    Author     : bmunoz
--%>
<%@page import="org.json.JSONObject"%>
<%@page import="org.json.JSONArray"%>
<%@page import="com.co.sio.java.dao.MenuDao"%>
<!DOCTYPE html>
<html lang="es"><!--Temporal mientras se define el layout-->
    <head>
        <title>SIO Cuentas M�dicas</title>
        <link rel="shortcut icon" href="recursos/img/favicon.ico" />
        <meta charset="UTF-8">
        <meta name="viewport"  content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" type="text/css" href="recursos/terceros/bootstrap/css/bootstrap.css" />
        <link rel="stylesheet" type="text/css" href="recursos/terceros/fontAwesome/css/font-awesome.min.css" />
        <link rel="stylesheet" type="text/css" media="screen" href="recursos/terceros/jqgrid/css/ui.jqgrid-bootstrap.css" />
       
        <link rel="stylesheet" type="text/css" href="recursos/css/general.css" />
        <link rel="stylesheet" type="text/css" href="recursos/css/home.css" />
        <link rel="stylesheet" type="text/css" href="recursos/css/menuPrincipal.css" />
        <!-- ----------------------------------------------------------------------------------------- -->
        <script type="text/javascript" src="recursos/terceros/jquery/js/jquery.min.js"></script>
        <script type="text/javascript" src="recursos/terceros/bootstrap/js/bootstrap.min.js"></script>
        <script type="text/javascript" src="recursos/terceros/jqgrid/js/i18n/grid.locale-es.js"></script>
        <!--script type="text/javascript" src="recursos/terceros/jqgrid/js/jquery.jqGrid.min.js"></script-->
        <script type="text/javascript" src="recursos/terceros/jqgrid/src/jquery.jqGrid.js"></script>
        <script type="text/javascript" src="recursos/js/general.js"></script> 
    </head>
    <body>
         <%
            if (null == session.getAttribute("usuario")) {
                response.sendRedirect("index.jsp");
            }
        %>
        <header>
            <div class="page-header">
                <img src="recursos/img/logo-header-1.png" id="logoheader" alt="Soluciones Integrales de Oficina" title="Soluciones Integrales de Oficina"/> 
                <span id="tituloheader">CUENTAS M�DICAS</span>
            </div>
        </header><!-- --------------------------------------------- -->
        <!-- Contenido del menu -->
        <div class="col-lg-3 col-md-3 " id="contentmenu">
            <aside>
                <div id="cabeceramenu">
                    <div class="col-lg-6 col-md-6 hidden-sm hidden-xs">
                        <i class="fa fa-bars" id="compresormenu"></i>
                    </div>
                    <div class="col-lg-6 col-md-6 ">
                        <img class="" src="recursos/img/sio.png" id="logo2" alt=""/>
                    </div>
                </div>
                <div class="accordion" id="leftMenu">
                    <div class="accordion-group">
                        <div class="accordion-heading" title="Inicio">
                            <a class="accordion-toggle linkhome activo" data-parent="#leftMenu" href="home.jsp">
                                <i class="fa fa-home"></i> <span class="titulosmenus">Inicio</span>
                            </a>
                        </div>
                    </div>
                    <% 
                       MenuDao menudao = new MenuDao();
                       if (session.getAttribute("usuario") != null){
                        String usuario = session.getAttribute("usuario").toString();
                        JSONArray json = new JSONArray(menudao.Crearmenu(usuario));
                        String Idrol =  session.getAttribute("Idrol").toString();
                        for (int i = 0; i < json.length(); i++) {
                             JSONObject row = json.getJSONObject(i);
                             int id = row.getInt("ID");
                             String menu = row.getString("MENU");
                             String identificador = row.getString("IDENTIFICADOR");
                             String icono = row.getString("ICONO");
                             out.println("<div class='accordion-group'>");
                                 out.println("<div class='accordion-heading' title='"+menu+"'>");
                                     out.println("<a class='accordion-toggle' id="+identificador+" data-toggle='collapse' data-parent='#leftMenu' href='#collapse"+identificador+"'>");
                                         out.println("<i class='"+icono+"'></i><span class='titulosmenus'>&nbsp;" +menu+"</span>");
                                     out.println("</a>");
                                 out.println("</div>");
                                 out.println("<div id='collapse"+identificador+"' class='accordion-body collapse' style='height: 0px; '>");
                                     out.println("<div class='accordion-inner'>");
                                          out.println("<ul>");
                                            JSONArray jsonchild = new JSONArray(menudao.Crearmenuhijos(id,Idrol));
                                            for (int j = 0; j < jsonchild.length(); j++) {
                                                JSONObject row2 = jsonchild.getJSONObject(j);
                                                String menuchild = row2.getString("HIJO");
                                                String childid = row2.getString("IDENTIFICADOR");
                                                out.println("<li><a href='#' id='"+childid+"'>"+menuchild+"</a></li>");
                                            }
                                         out.println("</ul>");
                                    out.println("</div>");
                                out.println("</div>");
                            out.println("</div>");
                        }
                    } 
                    %>
                </div>
            </aside>
        </div>
        <!-- ------------------------------------------------------------- -->
        
        <!-- contenido de la seccion principal -->
        <div class="col-lg-9 col-md-9 " id="contentsectionp">
            <section>
                <!--  barra de navegacion secundaria -->
                    <nav class="navbar navbar-default">
                        <div class="container-fluid">
                          <div class="navbar-header">
                            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                              <span class="sr-only">Toggle navigation</span>
                              <span class="icon-bar"></span>
                              <span class="icon-bar"></span>
                              <span class="icon-bar"></span>
                            </button>
                            <a class="navbar-brand" href="#">P�GINA PRINCIPAL</a>
                          </div>

                          <div class="collapse navbar-collapse menuperfil" id="bs-example-navbar-collapse-1">
                            <ul class="nav navbar-nav navbar-right">
                              <li class="dropdown">
                                  <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
                                    <i class="fa fa-user"> </i>
                                    <span id="nombreusuario"><%= session.getAttribute("Nombres")%> <%= session.getAttribute("Apellidos")%></span> 
                                    <span class="caret"></span></a>
                                <ul class="dropdown-menu">
                                    <li id="linkperfil"><a href="#">Mi Perfil</a></li>
                                    <li id="linkconfiguracion"><a href="#">Configuraci�n</a></li>
                                  <li><a href="index.jsp">Cerrar Sesi�n</a></li>
                                </ul>
                              </li>
                            </ul>
                          </div>
                        </div>
                      </nav>
                <!-- ------------------------------------- -->
                
                <!-- jumbotron -->
               <div class="col-lg-12 col-md-12  seccionjumbo"> 
                    <div class="jumbotron">
                      <h1>Bienvenido!</h1>
                      <p>...</p>
                      <p><a class="btn btn-primary btn-lg" href="#" role="button">Ayuda</a></p>
                    </div>
                </div>
                 <!-- ------------------------------------- -->
                    
                    <div class="row nuevapagina">
                        <div class="col-lg-4 col-md-4  seccioninfo">
                            <i class="fa fa-balance-scale"></i><br/>
                            Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod
                            tempor incididunt ut labore et dolore magna aliqua.
                        </div>
                        <div class="col-lg-4 col-md-4  seccioninfo">
                            <i class="fa fa-bar-chart"></i><br/>
                            Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod
                            tempor incididunt ut labore et dolore magna aliqua.
                        </div>
                        <div class="col-lg-4 col-md-4  seccioninfo">
                            <i class="fa fa-table"></i><br/>
                            Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod
                            tempor incididunt ut labore et dolore magna aliqua.
                        </div>
                    </div>
                    
                    <div class="row contenidotemporal">
                        <div class="col-lg-6 col-md-6  ultimaseccion">
                            <div class="bs-example">
                                <div class="panel-group" id="accordion">
                                    <div class="panel panel-default">
                                        <div class="panel-heading">
                                            <h4 class="panel-title">
                                                <a data-toggle="collapse" data-parent="#accordion" href="#collapseOne">1. Lorem ipsum dolor</a>
                                            </h4>
                                        </div>
                                        <div id="collapseOne" class="panel-collapse collapse in">
                                            <div class="panel-body">
                                                <p> Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod
                                                    tempor incididunt ut labore et dolore magna aliqua. 
                                                    <a href="#" target="_blank">Ayuda</a></p>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="panel panel-default">
                                        <div class="panel-heading">
                                            <h4 class="panel-title">
                                                <a data-toggle="collapse" data-parent="#accordion" href="#collapseTwo">2. Lorem ipsum dolor</a>
                                            </h4>
                                        </div>
                                        <div id="collapseTwo" class="panel-collapse collapse">
                                            <div class="panel-body">
                                                <p>Bootstrap is a powerful front-end framework for faster and easier web development. It is a collection of CSS and HTML conventions. <a href="http://www.tutorialrepublic.com/twitter-bootstrap-tutorial/" target="_blank">Learn more.</a></p>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="panel panel-default">
                                        <div class="panel-heading">
                                            <h4 class="panel-title">
                                                <a data-toggle="collapse" data-parent="#accordion" href="#collapsefour">3. Lorem ipsum dolor</a>
                                            </h4>
                                        </div>
                                        <div id="collapsefour" class="panel-collapse collapse">
                                            <div class="panel-body">
                                                <p>CSS stands for Cascading Style Sheet. CSS allows you to specify various style properties for a given HTML element such as colors, backgrounds, fonts etc. <a href="http://www.tutorialrepublic.com/css-tutorial/" target="_blank">Learn more.</a></p>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-6 col-md-6  ultimaseccion">
                            <div class="bs-example">
                                <div class="panel-group" id="accordion2">
                                    <div class="panel panel-default">
                                        <div class="panel-heading">
                                            <h4 class="panel-title">
                                                <a data-toggle="collapse" data-parent="#accordion2" href="#collapseuno">1. Lorem ipsum dolor</a>
                                            </h4>
                                        </div>
                                        <div id="collapseuno" class="panel-collapse collapse in">
                                            <div class="panel-body">
                                                <p> Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod
                                                    tempor incididunt ut labore et dolore magna aliqua. 
                                                    <a href="#" target="_blank">Ayuda</a></p>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="panel panel-default">
                                        <div class="panel-heading">
                                            <h4 class="panel-title">
                                                <a data-toggle="collapse" data-parent="#accordion2" href="#collapsedos">2. Lorem ipsum dolor</a>
                                            </h4>
                                        </div>
                                        <div id="collapsedos" class="panel-collapse collapse">
                                            <div class="panel-body">
                                                <p>Bootstrap is a powerful front-end framework for faster and easier web development. It is a collection of CSS and HTML conventions. <a href="http://www.tutorialrepublic.com/twitter-bootstrap-tutorial/" target="_blank">Learn more.</a></p>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="panel panel-default">
                                        <div class="panel-heading">
                                            <h4 class="panel-title">
                                                <a data-toggle="collapse" data-parent="#accordion2" href="#collapsetres">3. Lorem ipsum dolor</a>
                                            </h4>
                                        </div>
                                        <div id="collapsetres" class="panel-collapse collapse">
                                            <div class="panel-body">
                                                <p>CSS stands for Cascading Style Sheet. CSS allows you to specify various style properties for a given HTML element such as colors, backgrounds, fonts etc. <a href="http://www.tutorialrepublic.com/css-tutorial/" target="_blank">Learn more.</a></p>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div> 
                        </div>
                    </div>
            </section>
        </div>
        
        <div class="corte col-lg-12 col-md-12 "></div><!--Temporal mientras se define el layout-->
        <footer class="col-lg-12 col-md-12 ">
            <div id="footer">
                <p class="copyright">SIO S.A. | Cali: Cra 100 # 14 - 96 Barrio Ciudad Jard�n PBX: (57 2) 485 5757 - (572) 485 5758 </p>
                <p class="copyright1">Colombia</p>
            </div>
        </footer><!-- --------------------------------------------- -->
    </body>
</html>
