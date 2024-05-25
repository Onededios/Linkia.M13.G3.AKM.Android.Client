import android.util.Log
import com.linkiaM13G3.akmAndroidClient.API.Connection
import com.linkiaM13G3.akmAndroidClient.Clients.StatusClient
import com.linkiaM13G3.akmAndroidClient.Entities.App
import java.io.IOException

private val connection = Connection().appService
class AppClient {
    suspend fun fetchApps() : List<App>? {
        return try {
            connection.getApps()
        } catch (e: IOException) {
            Log.e("Apps", "Error fetching apps: ${e.message}")
            emptyList()
        }
    }
}
