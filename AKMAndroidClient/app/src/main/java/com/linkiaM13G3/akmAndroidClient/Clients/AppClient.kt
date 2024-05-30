
import android.util.Log
import com.linkiaM13G3.akmAndroidClient.API.Connection
import com.linkiaM13G3.akmAndroidClient.Entities.App
import retrofit2.HttpException

private val connection = Connection().appService
class AppClient {
    suspend fun fetchApps() : List<App>? {
        return try {
            connection.getApps()
        } catch (e: HttpException) {
            Log.e("Apps", "Error fetching apps: ${e.message}")
            emptyList()
        }
    }
}
