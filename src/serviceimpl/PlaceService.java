package serviceimpl;

import entity.News;
import entity.Place;
import severside.ServerBuffer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by keben on 2016/12/23.
 */
public class PlaceService extends ServiceBase{

    private static PlaceService ghs = null;

    public static PlaceService getPlaceService(){

        if(ghs==null){
            ghs = new PlaceService();
        }
        return ghs;
    }
    public void getPlace(){

        //获取链接
        Connection conn = getConnection();

        //mysql语句对象
        PreparedStatement findUS = null;


        //编译语句
        try {
            findUS = conn.prepareStatement("SELECT * FROM place");


            ResultSet rs = findUS.executeQuery();


            while (rs.next()){

                Place place = new Place();
                placeDataSet(place,rs);
                ServerBuffer.hmplace.put(place.getPlace_id(),place);



            }
            System.out.println(ServerBuffer.hmplace.get(1).getPlace_name()+"----------"+ServerBuffer.hmplace.get(3).getPlace_name());
            findUS.close();
            rs.close();


        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void placeDataSet(Place place,ResultSet rs) throws SQLException {
        place.setPlace_id(rs.getInt("place_id"));
        place.setPlace_name(rs.getString("place_name"));
    }

}
