package pe.com.foxsoft.reportsweb.prime.faces.managedbeans;

import java.io.PrintStream;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.faces.FacesException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import pe.com.foxsoft.reportsweb.jpa.data.EtiquetaProducto;
import pe.com.foxsoft.reportsweb.spring.service.ParametroService;
import pe.com.foxsoft.reportsweb.spring.service.ProductoService;

@ManagedBean
@SessionScoped
public class ProductoMB{
	
  protected final Logger logger = Logger.getLogger(getClass());
  
  @ManagedProperty("#{loginService}")
  private ProductoService objProductoService;
  
  @ManagedProperty("#{parametroService}")
  private ParametroService objParametroService;
  
  private EtiquetaProducto objProductoMain;
  
  private List<EtiquetaProducto> lstProductosMain;
  private List<ParametroDet> lstEstadosGenerales;
  private List<String> lstNomProductoBUS;
  private List<String> lstCodProductoBUS;
  private boolean validaListaBuscar = true;
  private int iCanRegTablaPrincipal;
  private boolean bflagConfirmEliProd = false;
  private Integer iIdEstadoMae;
  private String descProductoBUS;
  private String codproductoBUS;
  private EtiquetaProducto objProducto;
  private UploadedFile file;
  private byte[] lostbyte;
  
  public ProductoMB()
  {
    this.objProductoMain = new EtiquetaProducto();
    this.lstProductosMain = new ArrayList();
    this.lstEstadosGenerales = new ArrayList();
    this.lstCodProductoBUS = new ArrayList();
    this.lstNomProductoBUS = new ArrayList();
    this.objProducto = new EtiquetaProducto();
  }
  
  public void buscarProductos()
  {
    this.objProductoMain.setCodproductoBbva(this.codproductoBUS);
    this.objProductoMain.setDescProducto(this.descProductoBUS);
    
    System.out.println("ESTADO LLEGADO: " + this.iIdEstadoMae);
    try
    {
      this.validaListaBuscar = false;
      this.lstProductosMain = this.objProductoService.buscaProductos(this.objProductoMain, this.iIdEstadoMae.intValue());
      this.iCanRegTablaPrincipal = this.lstProductosMain.size();
    }
    catch (ExcepcionGeneral e)
    {
      String sMensaje = "No se puedo inicializar lstUsuariosBus";
      this.logger.error(e.getMessage());
      throw new FacesException(sMensaje, e);
    }
  }
  
  public void getFile(FileUploadEvent e)
  {
    System.out.println("RUTA 1: " + e.getFile().getFileName());
    this.lostbyte = e.getFile().getContents();
    System.out.println("SE GUARDO TODO LOS BYTES");
  }
  
  public void insertaProducto()
  {
    String sMensaje = "";
    
    System.out.println("PRODUCTO: " + this.objProductoMain.getDescProducto());
    System.out.println("CODIGO: " + this.objProductoMain.getCodproductoBbva());
    
    EtiquetaProducto objProducto = new EtiquetaProducto();
    try
    {
      if ((this.objProductoMain.getDescProducto() == "") || (this.objProductoMain.getCodproductoBbva() == ""))
      {
        Utilitarios.mensajeError("Campos Obligatorios", "Debe llenar los campos marcados con asterisco");
      }
      else
      {
        objProducto.setCodproductoBbva(this.objProductoMain.getCodproductoBbva().toUpperCase());
        objProducto.setDescProducto(this.objProductoMain.getDescProducto());
        
        System.out.println("APUNTO DE GUARDAR LA IMAGEN");
        Blob blob = Hibernate.createBlob(this.lostbyte);
        objProducto.setImagen(blob);
        
        sMensaje = this.objProductoService.insertarProducto(objProducto);
        Utilitarios.mensaje("", sMensaje);
        setLstProductosMain(new ArrayList());
        this.iCanRegTablaPrincipal = getListaPrinciaplProductos();
      }
    }
    catch (ExcepcionGeneral ex)
    {
      sMensaje = "No se puedo inicializar insertProveedor";
      this.logger.error(ex.getMessage());
      throw new FacesException(sMensaje, ex);
    }
  }
  
  public void openAgregarProducto()
  {
    this.objProductoMain = new EtiquetaProducto();
    this.objProductoMain.setCodproductoBbva("");
    this.objProductoMain.setDescProducto("");
  }
  
  public void openEditarProducto()
  {
    setObjProductoMain(new EtiquetaProducto());
    
    Map<String, String> paramMap = FacesContext.getCurrentInstance()
      .getExternalContext().getRequestParameterMap();
    
    long codProd = Long.parseLong((String)paramMap.get("codProd"));
    System.out.println("ME LLEGO EL CODIGO DE PRODUCTO EN EL PARAMETRO: " + 
      codProd);
    try
    {
      this.objProductoMain = this.objProductoService.obtenerProducto(codProd);
      this.iIdEstadoMae = Integer.valueOf(this.objProductoMain.getParametroDet().getIdParamDet());
    }
    catch (ExcepcionGeneral ex)
    {
      String sMensaje = "No se puedo inicializar openEditProveedor";
      this.logger.error(ex.getMessage());
      throw new FacesException(sMensaje, ex);
    }
  }
  
  public void editarProducto()
  {
    String sMensaje = "";
    
    System.out.println("DESCRIPCION: " + this.objProductoMain.getDescProducto());
    System.out.println("CODIGO: " + this.objProductoMain.getCodproductoBbva());
    System.out.println("ESTADO: " + this.iIdEstadoMae);
    
    ParametroDet objParamDet = new ParametroDet();
    objParamDet.setIdParamDet(this.iIdEstadoMae.intValue());
    try
    {
      if (this.objProductoMain.getDescProducto() == "")
      {
        Utilitarios.mensajeError("", "Ingrese la descripci�n del producto");
      }
      else if (this.objProductoMain.getCodproductoBbva() == "")
      {
        Utilitarios.mensajeError("", "Ingrese el c�digo BBVA del producto");
      }
      else if (this.iIdEstadoMae.intValue() == -1)
      {
        Utilitarios.mensajeError("", "Seleccione el estado");
      }
      else
      {
        System.out.println("APUNTO DE GUARDAR LA IMAGEN");
        Blob blob = Hibernate.createBlob(this.lostbyte);
        this.objProductoMain.setImagen(blob);
        
        this.objProductoMain.setParametroDet(objParamDet);
        sMensaje = this.objProductoService.editarProducto(this.objProductoMain);
        Utilitarios.mensaje("", sMensaje);
        this.iCanRegTablaPrincipal = getListaPrinciaplProductos();
      }
    }
    catch (ExcepcionGeneral ex)
    {
      sMensaje = "No se puedo inicializar openEditProveedor";
      this.logger.error(ex.getMessage());
      throw new FacesException(sMensaje, ex);
    }
  }
  
  public void visibleConfirmElimProducto()
  {
    this.bflagConfirmEliProd = true;
  }
  
  public void eliminarProducto()
  {
    this.logger.info("ELIMINAR PRODUCTO");
    String sMensaje = "";
    System.out.println("PRODUCTO A ELIMINAR: " + this.objProductoMain.getIdProducto());
    try
    {
      sMensaje = this.objProductoService.eliminarProducto(this.objProductoMain);
      setLstProductosMain(new ArrayList());
      this.iCanRegTablaPrincipal = getListaPrinciaplProductos();
      Utilitarios.mensaje("", sMensaje);
    }
    catch (ExcepcionGeneral ex)
    {
      sMensaje = "No se puedo inicializar openEditProveedor";
      this.logger.error(ex.getMessage());
      throw new FacesException(sMensaje, ex);
    }
    this.bflagConfirmEliProd = false;
  }
  
  public int getListaPrinciaplProductos()
  {
    int can = 0;
    this.objProductoService = new ProductoServiceImpl();
    try
    {
      this.lstProductosMain = this.objProductoService.getListaProductos();
      can = this.lstProductosMain.size();
      for (EtiquetaProducto p : this.lstProductosMain)
      {
        if (p.getCodproductoBbva() != null) {
          this.lstCodProductoBUS.add(p.getCodproductoBbva());
        }
        if (p.getDescProducto() != null) {
          this.lstNomProductoBUS.add(p.getDescProducto());
        }
      }
    }
    catch (ExcepcionGeneral e)
    {
      String sMensaje = "No se puedo inicializar lstMaestrosMain";
      this.logger.error("No se puedo inicializar lstMaestrosMain", e);
      throw new FacesException(sMensaje, e);
    }
    return can;
  }
  
  public void obtenerEstadosProductos()
  {
    this.objParametroService = new ParametroServiceImpl();
    try
    {
      this.lstEstadosGenerales = this.objParametroService
        .obtenerTiposDeEstadosGenerales("TAB015");
    }
    catch (ExcepcionGeneral ex)
    {
      String sMensaje = "No se puedo inicializar lstEstados";
      this.logger.error("No se puedo inicializar lstEstados", ex);
      throw new FacesException(sMensaje, ex);
    }
  }
  
  public List<String> completeNom(String query)
  {
    List<String> results = new ArrayList();
    for (String s : this.lstNomProductoBUS) {
      if (Utilitarios.compararCadenas(s, query.trim())) {
        results.add(s);
      }
    }
    return results;
  }
  
  public List<String> completeCod(String query)
  {
    List<String> results = new ArrayList();
    for (String s : this.lstCodProductoBUS) {
      if (Utilitarios.compararCadenas(s, query.trim())) {
        results.add(s);
      }
    }
    return results;
  }
  
  public EtiquetaProducto getObjProductoMain()
  {
    return this.objProductoMain;
  }
  
  public void setObjProductoMain(EtiquetaProducto objProductoMain)
  {
    this.objProductoMain = objProductoMain;
  }
  
  public List<EtiquetaProducto> getLstProductosMain()
  {
    if ((this.lstProductosMain.isEmpty()) && (this.validaListaBuscar)) {
      this.iCanRegTablaPrincipal = getListaPrinciaplProductos();
    }
    return this.lstProductosMain;
  }
  
  public void setLstProductosMain(List<EtiquetaProducto> lstProductosMain)
  {
    this.lstProductosMain = lstProductosMain;
  }
  
  public int getiCanRegTablaPrincipal()
  {
    return this.iCanRegTablaPrincipal;
  }
  
  public void setiCanRegTablaPrincipal(int iCanRegTablaPrincipal)
  {
    this.iCanRegTablaPrincipal = iCanRegTablaPrincipal;
  }
  
  public Integer getiIdEstadoMae()
  {
    return this.iIdEstadoMae;
  }
  
  public void setiIdEstadoMae(Integer iIdEstadoMae)
  {
    this.iIdEstadoMae = iIdEstadoMae;
  }
  
  public String getDescProductoBUS()
  {
    return this.descProductoBUS;
  }
  
  public void setDescProductoBUS(String descProductoBUS)
  {
    this.descProductoBUS = descProductoBUS;
  }
  
  public String getCodproductoBUS()
  {
    return this.codproductoBUS;
  }
  
  public void setCodproductoBUS(String codproductoBUS)
  {
    this.codproductoBUS = codproductoBUS;
  }
  
  public List<ParametroDet> getLstEstadosGenerales()
  {
    if (this.lstEstadosGenerales.isEmpty()) {
      obtenerEstadosProductos();
    }
    return this.lstEstadosGenerales;
  }
  
  public void setLstEstadosGenerales(List<ParametroDet> lstEstadosGenerales)
  {
    this.lstEstadosGenerales = lstEstadosGenerales;
  }
  
  public EtiquetaProducto getObjProducto()
  {
    return this.objProducto;
  }
  
  public void setObjProducto(EtiquetaProducto objProducto)
  {
    this.objProducto = objProducto;
  }
  
  public boolean isBflagConfirmEliProd()
  {
    return this.bflagConfirmEliProd;
  }
  
  public void setBflagConfirmEliProd(boolean bflagConfirmEliProd)
  {
    this.bflagConfirmEliProd = bflagConfirmEliProd;
  }
  
  public List<String> getLstNomProductoBUS()
  {
    return this.lstNomProductoBUS;
  }
  
  public void setLstNomProductoBUS(List<String> lstNomProductoBUS)
  {
    this.lstNomProductoBUS = lstNomProductoBUS;
  }
  
  public List<String> getLstCodProductoBUS()
  {
    return this.lstCodProductoBUS;
  }
  
  public void setLstCodProductoBUS(List<String> lstCodProductoBUS)
  {
    this.lstCodProductoBUS = lstCodProductoBUS;
  }
  
  public boolean isValidaListaBuscar()
  {
    return this.validaListaBuscar;
  }
  
  public void setValidaListaBuscar(boolean validaListaBuscar)
  {
    this.validaListaBuscar = validaListaBuscar;
  }
  
  public UploadedFile getFile()
  {
    return this.file;
  }
  
  public void setFile(UploadedFile file)
  {
    this.file = file;
  }
  
  public byte[] getLostbyte()
  {
    return this.lostbyte;
  }
  
  public void setLostbyte(byte[] lostbyte)
  {
    this.lostbyte = lostbyte;
  }
}
