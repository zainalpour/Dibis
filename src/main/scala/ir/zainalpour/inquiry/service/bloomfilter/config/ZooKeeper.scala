package ir.sahab.inquiry.service.bloomfilter.config

import java.nio.charset.Charset

import com.google.common.base.Charsets
import org.apache.curator.framework.CuratorFramework

import scala.util.Try

/**
 * <h1>Zookeeper</h1>
 * The Zookeeper 
 *
 * @author  Mohsen Zainalpour
 * @version 1.0
 * @since   5/10/15 
 */
trait ZooKeeper {
  /**
   * Retrieves raw value from remote configuration.
   *
   * @param path path to configuration entry; for example, <i>section.subsection.entry</i>
   * @param client ZooKeeper remote config client
   * @return configuration setting value
   */
  def getSetting(path: String)(implicit client: CuratorFramework): Array[Byte] = {
    client.getData.forPath("/%s".format(path.trim.replaceAll("\\.", "/")))
  }

  /**
   * Retrieves optional raw value from remote configuration.
   *
   * @param path path to configuration entry; for example, <i>section.subsection.entry</i>
   * @param client remote configuration client
   * @return optional configuration setting value
   */
  def getOptionalSetting(path: String)(implicit client: CuratorFramework): Option[Array[Byte]] = {
    Try {
      getSetting(path)
    }.toOption
  }

  /**
   * Helps to convert data from ZooKeeper into base data types.
   *
   * @param zData raw data from ZooKeeper
   * @param charset charset to use for data conversion
   */
  implicit class ZDataConverter(val zData: Array[Byte])(implicit val charset: Charset = Charsets.UTF_8) {

    /**
     * Converts data from ZooKeeper to string, if possible.
     *
     * @return string value
     */
    def asString: String = new String(zData, charset)

    /**
     * Converts data from ZooKeeper to boolean, if possible.
     *
     * @return boolean value
     * @throws IllegalArgumentException if can not cast value to boolean
     */
    def asBoolean: Boolean = new String(zData, charset).toBoolean

    /**
     * Converts data from ZooKeeper to byte, if possible.
     *
     * @return byte value
     * @throws NumberFormatException if value is not a valid byte
     */
    def asByte: Byte = new String(zData, charset).toByte

    /**
     * Converts data from ZooKeeper to int, if possible.
     *
     * @return int value
     * @throws NumberFormatException if value is not a valid integer
     */
    def asInt: Int = new String(zData, charset).toInt

    /**
     * Converts data from ZooKeeper to long, if possible.
     *
     * @return long value
     * @throws NumberFormatException if value is not a valid long
     */
    def asLong: Long = new String(zData, charset).toLong

    /**
     * Converts data from ZooKeeper to double, if possible.
     *
     * @return double value
     * @throws NumberFormatException if value is not a valid double
     */
    def asDouble: Double = new String(zData, charset).toDouble
  }

  /**
   * Helps to convert optional data from ZooKeeper into base data types and wrapped in Option.
   *
   * @param zDataOption data from ZooKeeper, wrapped in Option
   * @param charset charset to use for data conversion
   */
  implicit class ZDataOptionConverter(val zDataOption: Option[Array[Byte]])(implicit val charset: Charset = Charsets.UTF_8) {

    /**
     * Converts data from ZooKeeper to optional string, if possible.
     *
     * @return optional string value
     */
    def asOptionalString: Option[String] = zDataOption.map(_.asString)

    /**
     * Converts data from ZooKeeper to optional boolean, if possible.
     *
     * @return optional boolean value
     */
    def asOptionalBoolean: Option[Boolean] = zDataOption.flatMap(v => Try {
      v.asBoolean
    }.toOption)

    /**
     * Converts data from ZooKeeper to optional byte, if possible.
     *
     * @return optional byte value
     */
    def asOptionalByte: Option[Byte] = zDataOption.flatMap(v => Try {
      v.asByte
    }.toOption)

    /**
     * Converts data from ZooKeeper to optional integer, if possible.
     *
     * @return optional int value
     */
    def asOptionalInt: Option[Int] = zDataOption.flatMap(v => Try {
      v.asInt
    }.toOption)

    /**
     * Converts data from ZooKeeper to optional long, if possible.
     *
     * @return optional long value
     */
    def asOptionalLong: Option[Long] = zDataOption.flatMap(v => Try {
      v.asLong
    }.toOption)

    /**
     * Converts data from ZooKeeper to optional double, if possible.
     *
     * @return optional double value
     */
    def asOptionalDouble: Option[Double] = zDataOption.flatMap(v => Try {
      v.asDouble
    }.toOption)
  }

}
