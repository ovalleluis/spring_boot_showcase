package showcase

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import java.util.logging.Logger



/**
 * Created by luis.ovalle on 30/01/2017.
 */
@SpringBootApplication
open class Application {

    companion object {
        val log = Logger.getLogger(Application.javaClass.name)
    }

}


fun main(args: Array<String>) {
    SpringApplication.run(Application::class.java, *args)
    Application.log.info { "Started as a kotlin application" }
}