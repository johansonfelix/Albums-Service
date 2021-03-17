package com.rest;

import com.Albums;

import com.google.gson.Gson;
import exceptions.RepException;
import org.glassfish.jersey.media.multipart.FormDataParam;
import pojo.Album;
import pojo.CoverImage;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.*;
import java.util.ArrayList;

import java.util.HashMap;
import java.util.Map;


@Path("album")
public class AlbumRest{

    private static Albums albums = Albums.getAlbumsInstance();




    @Path("createAlbum")
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response createNewAlbum(Album album) {


        try {
            if(album.getISRC() == null)
                throw new RepException("Invalid Album - no ISRC");

            String response = albums.createAlbum(album);
            if (response.contains("CREATED")) {
                Gson gson = new Gson();
                Map<String, String> map = new HashMap<>();
                map.put("status", "success");
                map.put("message", response);

                return Response.ok(gson.toJson(map)).build();

            } else {
                throw new RepException(response);
            }
        }
        catch(RepException e) {
            return getResponse(e);
        }

    }

    @Path("updateAlbum")
    @PUT
    @Consumes("application/json")
    @Produces("application/json")
    public Response updateAlbum(Album album) throws IOException {


        try{
            if(album.getISRC() == null)
                throw new RepException("Invalid Album - no ISRC");

            String response = albums.updateAlbum(album);

            if (response.contains("UPDATED")){
                return getResponse(response);
            }
            else{
                throw new RepException(response);
            }
        }
        catch (RepException e){
            return getResponse(e);
        }

    }

    @Path("deleteAlbum/{ISRC}")
    @DELETE
    @Produces("application/json")
    public Response deleteAlbum(@QueryParam("ISRC") String ISRC) {


        try{
            String response = albums.deleteAlbum(ISRC);
            if(response.contains("DELETED")) {

                return getResponse(response);
            }
            else{

                throw  new RepException(response);
            }
        }
        catch (RepException e){
            return getResponse(e);

        }

    }

    @Path("getAlbum/{ISRC}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAlbum(@PathParam("ISRC") String ISRC){

        try{
            Album info = albums.getAlbumInfo(ISRC);

            if(info == null)
                throw new RepException("Album does not exist");
            else {
                System.out.println(info.toString());

                return Response.ok(info).build();
            }
        }
        catch (RepException e){

            return getResponse(e);
        }

    }

    @Path("getAlbums")
    @GET
    @Produces("application/json")
    public ArrayList<Album> getAlbum(){
        return albums.getAlbumsList();

    }

    @Path("deleteAlbumCover/{ISRC}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteAlbumCover(@QueryParam("ISRC") String ISRC){

        try {
            String response = albums.deleteAlbumCoverImage(ISRC);

            return getResponse(response);
        }
        catch (RepException e){

           return getResponse(e);
        }
    }

    @Path("updateAlbumCover/{ISRC}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateAlbumCover(CoverImage image, @QueryParam("ISRC") String ISRC) {

        try {

            String response = albums.updateAlbumCoverImage(ISRC, image);

            return getResponse(response);

        } catch (RepException e) {
            return getResponse(e);

        }


    }
    @Path("getAlbumCover/{ISRC}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAlbumCover(@QueryParam("ISRC") String ISRC){

        try{
            CoverImage coverImage = albums.getAlbumCoverImage(ISRC);

            if (coverImage.getBase64atatchment() == null){
                throw  new RepException("No image");
            }
            else{
                Gson gson = new Gson();
                Map<String, String> map = new HashMap<>();
                map.put("status", "success");
                map.put("cover image bytes", coverImage.getBase64atatchment());
                map.put("MIME Type", coverImage.getMimeType());

                return Response.ok(gson.toJson(map)).build();

            }

        }
        catch(RepException e){
            return getResponse(e);
        }

    }

    

    private Response getResponse(Exception e) {
        Gson gson = new Gson();
        Map<String, String> map = new HashMap<>();
        map.put("status", "fail");
        map.put("message", e.getMessage());

        return Response.ok(gson.toJson(map)).build();
    }

    private Response getResponse(String response) throws RepException {

            Gson gson = new Gson();
            Map<String, String> map = new HashMap<>();
            map.put("status", "success");
            map.put("message", response);

            return Response.ok(gson.toJson(map)).build();

    }


}
