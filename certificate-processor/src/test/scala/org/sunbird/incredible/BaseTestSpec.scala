package org.sunbird.incredible


import java.util

import org.apache.commons.collections.MapUtils
import org.apache.commons.lang.StringUtils
import org.scalatest.{BeforeAndAfterAll, FlatSpec, Matchers}
import org.scalatestplus.mockito.MockitoSugar

class BaseTestSpec extends FlatSpec with Matchers with BeforeAndAfterAll with MockitoSugar {


  def populatesPropertiesMap(req: util.Map[String, AnyRef]): Map[String, String] = {
    val basePath: String = req.getOrDefault(req.get(JsonKeys.BASE_PATH), "http://localhost:9000").asInstanceOf[String]
    val tag: String = "0131685518070087685"
    var properties: Map[String, String] = Map(JsonKeys.BASE_PATH -> basePath, JsonKeys.TAG -> tag,
      JsonKeys.BADGE_URL -> basePath.concat(if (StringUtils.isNotBlank(tag)) "/".concat(tag).concat(JsonKeys.BADGE_URL) else JsonKeys.BADGE_URL),
      JsonKeys.ISSUER_URL -> basePath.concat(JsonKeys.ISSUER_URL), JsonKeys.EVIDENCE_URL -> basePath.concat(JsonKeys.EVIDENCE_URL),
      JsonKeys.CONTEXT -> basePath.concat(JsonKeys.CONTEXT), JsonKeys.ACCESS_CODE_LENGTH -> "6", JsonKeys.ENC_SERVICE_URL -> "http://localhost:8013", JsonKeys.SIGNATORY_EXTENSION -> String.format("%s/%s/%s", basePath, JsonKeys.SIGNATORY_EXTENSION, "context.json"))
    val keysObject: util.Map[String, AnyRef] = req.get(JsonKeys.KEYS).asInstanceOf[util.Map[String, AnyRef]]
    if (MapUtils.isNotEmpty(keysObject)) {
      val keyId: String = keysObject.get(JsonKeys.ID).asInstanceOf[String]
      properties += (JsonKeys.KEY_ID -> keyId)
      properties += (JsonKeys.SIGN_CREATOR -> basePath.concat("/").concat(keyId).concat(JsonKeys.PUBLIC_KEY_URL))
      properties += (JsonKeys.PUBLIC_KEY_URL -> basePath.concat("/").concat(JsonKeys.KEYS).concat("/").concat(keyId).concat(JsonKeys.PUBLIC_KEY_URL))
    }
    properties
  }

}