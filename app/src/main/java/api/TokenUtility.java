//package api;
//
//import static android.content.Context.MODE_PRIVATE;
//
//import android.content.Context;
//import android.content.SharedPreferences;
//
//import com.example.covidtestreport.R;
//
//import model.TokenRes;
//import retrofit2.Call;
//import retrofit2.Response;
//
//public class TokenUtility {
//
//
//    private static String saveToken(TokenRes tokenRes, Context context) {
//        SharedPreferences.Editor sharedPreferences = context.getSharedPreferences(context.getString(R.string.Auth), MODE_PRIVATE).edit();
//        sharedPreferences.putString(context.getString(R.string.token), tokenRes.getAccessToken());
//        sharedPreferences.apply();
//        return tokenRes.getAccessToken();
//    }
//
//    static String getSavedToken(Context context) {
//        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.Auth), MODE_PRIVATE);
//        return sharedPreferences.getString(context.getString(R.string.token), null);
//    }
//
//    static String getAccessToken(Context context) {
//
//        APIInterface apiService = APIClient.getClientWithoutToken(context).create(APIInterface.class);
//        Call<TokenRes> call = apiService.getToken(context.getString(R.string.username),context.getString(R.string.password),context.getString(R.string.grandtype));
//        String accessToken = null;
//        Response<TokenRes> response = null;
//        try {
//            response = call.execute();
//            if (response.isSuccessful()) {
//                TokenRes tokenRes = (TokenRes) response.body();
//                accessToken = saveToken(tokenRes, context);
//            }
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//        return accessToken;
//    }
//
//}
