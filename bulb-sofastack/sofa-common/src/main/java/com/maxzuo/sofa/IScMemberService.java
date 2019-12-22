package com.maxzuo.sofa;

import javax.ws.rs.*;

/**
 * Created by zfh on 2019/12/22
 */
@Path("webapi")
@Consumes("application/json;charset=UTF-8")
@Produces("application/json;charset=UTF-8")
public interface IScMemberService {

    @GET
    @Path("sayName/{name}/{age}")
    String sayName(@PathParam("name") String name, @PathParam("age") Integer age);

    @GET
    @Path("info")
    String info();
}
