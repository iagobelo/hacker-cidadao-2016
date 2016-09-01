package br.com.rotaverde.rotaverde.model;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by iagobelo on 18/08/16.
 */
public class Tree {
    private String id;
    private String microRegion;
    private String observation;
    private String number;
    private String rpa;
    private String latitude;
    private String longitude;
    private String family;
    private String decree;
    private String scientificName;
    private String adress;
    private String popularName;
    private String identifies;
    private String status;
    private String userCode;

    public Tree() {

    }

    public Tree(String id, String microRegion, String observation, String number, String rpa,
                String latitude, String longitude, String family, String decree, String scientificName,
                String adress, String popularName, String identifies, String status, String userCode) {
        this.id = id;
        this.microRegion = microRegion;
        this.observation = observation;
        this.number = number;
        this.rpa = rpa;
        this.latitude = latitude;
        this.longitude = longitude;
        this.family = family;
        this.decree = decree;
        this.scientificName = scientificName;
        this.adress = adress;
        this.popularName = popularName;
        this.identifies = identifies;
        this.status = status;
        this.userCode = userCode;
    }

    public MarkerOptions toMarkerOptions() {
        LatLng treePosition = new LatLng(Double.parseDouble(this.getLatitude()), Double.parseDouble(this.getLongitude()));
        return new MarkerOptions().position(treePosition).title(this.getPopularName());
    }

    @Override
    public String toString() {
        return "Tree{" +
                "id='" + id + '\'' +
                ", microRegion='" + microRegion + '\'' +
                ", observation='" + observation + '\'' +
                ", number='" + number + '\'' +
                ", rpa='" + rpa + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", family='" + family + '\'' +
                ", decree='" + decree + '\'' +
                ", scientificName='" + scientificName + '\'' +
                ", adress='" + adress + '\'' +
                ", popularName='" + popularName + '\'' +
                ", identifies='" + identifies + '\'' +
                ", status='" + status + '\'' +
                ", userCode='" + userCode + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMicroRegion() {
        return microRegion;
    }

    public void setMicroRegion(String microRegion) {
        this.microRegion = microRegion;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getRpa() {
        return rpa;
    }

    public void setRpa(String rpa) {
        this.rpa = rpa;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public String getDecree() {
        return decree;
    }

    public void setDecree(String decree) {
        this.decree = decree;
    }

    public String getScientificName() {
        return scientificName;
    }

    public void setScientificName(String scientificName) {
        this.scientificName = scientificName;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getPopularName() {
        return popularName;
    }

    public void setPopularName(String popularName) {
        this.popularName = popularName;
    }

    public String getIdentifies() {
        return identifies;
    }

    public void setIdentifies(String identifies) {
        this.identifies = identifies;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }
}
