/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.map;

import dao.CategoryDAO;
import dao.UserDAO;
import entities.Category;
import entities.User;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import org.primefaces.event.map.GeocodeEvent;
import org.primefaces.event.map.MarkerDragEvent;
import org.primefaces.event.map.PointSelectEvent;
import org.primefaces.event.map.ReverseGeocodeEvent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.GeocodeResult;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;


@ManagedBean
@ViewScoped
public class MapControler implements Serializable {

    private MapModel model;
    private String centerMap;
    private String address;
    private Marker marker;
    private Double  lat;
    private Double  lng;
    
    private String country;
    private String zipCode;
    private String city;
    
   
    private String addr;

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }
   
    

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
   
    

    public MapModel getModel() {
        return model;
    }

    public void setModel(MapModel model) {
        this.model = model;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Marker getMarker() {
        return marker;
    }

    public void setMarker(Marker marker) {
        this.marker = marker;
    }
    
    public String getCenterMap() {
        return centerMap;
    }

    
     
    @PostConstruct
    public void MapControler() {
        model = new DefaultMapModel();
        LatLng coord1 = new LatLng(37.9833333, 23.7333333);
        this.centerMap = coord1.getLat() + "," + coord1.getLng();
        
//        marker = new Marker(coord1, "Athens");
//        model.addOverlay(marker);
//           
//         
//        for(Marker premarker : model.getMarkers()) {
//            premarker.setDraggable(true);
//        }
//        
    }
      
    public String draggable(){
       List<Marker> markerList = this.model.getMarkers();
       if(markerList.get(0)!=null){
           markerList.get(0).setDraggable(true);
       }
       return null;
    }
   
    public void onMarkerDrag(MarkerDragEvent event) {
        marker = event.getMarker(); 
        this.lat=marker.getLatlng().getLat();
        this.lng=marker.getLatlng().getLng();
        
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Marker Dragged", "Lat:" + marker.getLatlng().getLat() + ", Lng:" + marker.getLatlng().getLng()));
    }
    
    
    private boolean deactivateEvent ;

    public boolean isDeactivateEvent() {
        return deactivateEvent;
    }

    public void setDeactivateEvent(boolean deactivateEvent) {
        this.deactivateEvent = deactivateEvent;
    }
    
    public void pointSelectEvent(PointSelectEvent click){
        
        LatLng coord1 = click.getLatLng();
        if(marker == null){
            marker = new Marker(coord1, "Home");
            marker.setDraggable(true);
            model.addOverlay(marker);
            deactivateEvent = true;
          
            this.lat=marker.getLatlng().getLat();
            this.lng=marker.getLatlng().getLng();
        
        }
        
    }
    
     public void onGeocode(GeocodeEvent event) {
        List<GeocodeResult> results = event.getResults();
         
        if (results != null && !results.isEmpty()) {
            LatLng center = results.get(0).getLatLng();
            this.centerMap = center.getLat() + "," + center.getLng();
             
            for (int i = 0; i < results.size(); i++) {
                GeocodeResult result = results.get(i);
                if(this.marker==null){
                    this.marker= new Marker(result.getLatLng(),result.getAddress());
                    marker.setDraggable(true);
                    model.addOverlay(marker);
                }
                else{
                    this.marker.setLatlng(result.getLatLng());
                    this.marker.setTitle(result.getAddress());
                }                
               
                this.lat=marker.getLatlng().getLat();
                this.lng=marker.getLatlng().getLng();
        
            }
        }
    }
    
    public void onReverseGeocode(ReverseGeocodeEvent event) {
        
        List<String> addresses = event.getAddresses();
        LatLng coord = event.getLatlng();
        address= addresses.get(0);
        String [] table = address.split(",");
        this.addr=table[0];
        String [] table2 = table[1].split(" ");
        this.zipCode = table2[table.length-2]+table2[table.length-1];
        this.city = table2[0];
        this.country=table[table.length-1];
        
         
        if (addresses != null && !addresses.isEmpty()) {
            
            
        }
    }
    
    
    
    
}
