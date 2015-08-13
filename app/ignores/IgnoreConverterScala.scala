package ignores

import com.wordnik.swagger.converter.SwaggerSchemaConverter

/**
 * Created by lcamilo on 8/8/15.
 */

class IgnoreConverterScala extends SwaggerSchemaConverter {
  override def skippedClasses: Set[String] = Set("com.avaje.ebean.bean.EntityBeanIntercept")
  override def ignoredClasses: Set[String] = Set("com.avaje.ebean.bean.EntityBeanIntercept")
  override def ignoredPackages: Set[String] = Set("com.avaje.ebean")
}