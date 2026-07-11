
package work.part2.section3

import com.google.inject.Guice
import ixias.core.inject.IxiasModuleLoader

/**
 * DI コンテナ（Guice インジェクタ）を 1 つ組み立てて公開する。
 * getInstance で必要なインスタンスを取り出す（object の main から使う）。
 */
object DIContainer:
  private val injector = Guice.createInjector(AppModule(), IxiasModuleLoader())

  /** 型を渡すと、依存を解決したインスタンスを返す */
  def getInstance[A](clazz: Class[A]): A = injector.getInstance(clazz)
