package rest;

import com.Albums;
import com.google.gson.Gson;
import exceptions.RepException;
import pojo.Album;


import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
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
            Gson gson = new Gson();
            Map<String, String> map = new HashMap<>();
            map.put("status", "fail");
            map.put("message", e.getMessage());

            return Response.ok(gson.toJson(map)).build();
        }

    }

    @Path("updateAlbum")
    @PUT
    @Consumes("application/json")
    @Produces("text/plain")
    public Response updateAlbum(Album album) throws IOException {
        String response = albums.updateAlbum(album);
        try{
            if(response.contains("UPDATED")) {
                Gson gson = new Gson();
                Map<String, String> map = new HashMap<>();
                map.put("status", "success");
                map.put("message", response);

                return Response.ok(gson.toJson(map)).build();
            }
            else{
                throw  new RepException(response);
            }
        }
        catch (RepException e){
            Gson gson = new Gson();
            String json = gson.toJson(e);
            System.out.println(e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(e.toString()).build();

        }

    }

    @Path("deleteAlbum")
    @PUT
    @Consumes("application/json")
    @Produces("application/json")
    public Response deleteAlbum(Album album) {


        try{
            String response = albums.deleteAlbum(album.getISRC());
            if(response.contains("DELETED")) {
                Gson gson = new Gson();
                Map<String, String> map = new HashMap<>();
                map.put("status", "success");
                map.put("message", response);

                return Response.ok(gson.toJson(map)).build();
            }
            else{
                throw  new RepException(response);
            }
        }
        catch (RepException e){
            Gson gson = new Gson();
            String json = gson.toJson(e);
            System.out.println(e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(e.toString()).build();

        }

    }

    @Path("getAlbum/{ISRC}")
    @GET
    @Produces("application/json")
    public Response getAlbum(@PathParam("ISRC") String ISRC){

        try{
            Album info = albums.getAlbumInfo(ISRC);

            if(info == null)
                throw new RepException("Album does not exist");
            else {

                return Response.ok(info, MediaType.APPLICATION_JSON).build();
            }
        }
        catch (RepException e){
            Gson gson = new Gson();
            String json = gson.toJson(e);
            System.out.println(e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(e.toString()).build();
        }

    }

    @Path("getAlbums")
    @GET
    @Produces("application/json")
    public ArrayList<Album> getAlbum(){
        return albums.getAlbumsList();

    }







}
