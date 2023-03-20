package com.github.hpopov.daqhats.test;

public class JavaServer {

    public static void main(String[] args) {
        JavaServer client = new JavaServer();
        RequestData request = new RequestData();
        request.setRadius(5);
        request.setShape("Sphere");
        ResponseData response = client.calculatevolume_in_cplusplus(request);
        System.out.println("Response-> " + "Volume-> " + response.getVolume() + " Status->" + response.getStatus());
    }

    static {
        System.loadLibrary("daqhats-java");
    }

    public JavaServer() {
        System.out.println("JavaServer created");
    }

    public ResponseData calculatevolume_in_java(RequestData request) {
        System.out.println("Java -> Calculate Volume called");
        ResponseData response = new ResponseData();
        if (request.getShape().equals("Sphere")) {
            response.setVolume((4 * 22 * request.getRadius() * request.getRadius() * request.getRadius()) / (3 * 7));
        } else {
            response.setStatus("NOK");
        }
        response.setStatus("OK");
        return response;
    }

    private native ResponseData calculatevolume_in_cplusplus(RequestData request);
}