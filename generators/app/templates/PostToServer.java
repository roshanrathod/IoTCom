
import java.util.HashMap;
//Add RetroFit libraries to your project
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.http.*;
import retrofit.Callback;

/**
 *		Generated Using IoT communication framework generator
 */
 
public class PostToServer {

    public static final String API_URL = "<%= ServerAddress1 %>";

    private static class postData {
		
         <%= variables %>
        
		 <%= constructor %>
		 
        postData(String Name,String ID) {
            this.Name = Name;
            this.ID = ID;
        }

    }
        interface PostTo {
            @Headers( "Content-Type: application/json" )
           @POST("/IOTData")
            public void IOTData(@Body HashMap<String, String> data, Callback<postData> callback);
        }

        public void sendData(<%= variablesWithDatatypes %>) {

            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setLogLevel(RestAdapter.LogLevel.FULL)
                    .setEndpoint(API_URL)
                    .build();

            HashMap<String, String> data= new HashMap<>();
			<%= putVariablesInMap %>
            
            PostTo post = restAdapter.create(PostTo.class);
            new postData(Name,ID);
            try {


                post.IOTData(data, new Callback<postData>() {
                    @Override
                    public void success(postData data, Response response) {
                        System.out.println("Success");
                    }

                    @Override
                    public void failure(RetrofitError retrofitError) {
                        System.out.println(retrofitError.getMessage());
                    }
                });
            }
            catch(RetrofitError e){
                System.out.println(e.getMessage());
            }
        }
    }

