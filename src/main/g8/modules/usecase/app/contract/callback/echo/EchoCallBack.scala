package contract.callback.echo

import contract.CallBack

/**
 * EchoCallBack.
 */
sealed trait EchoCallBack

trait DebugEchoCallBack extends CallBack[String] with EchoCallBack

trait HealthCheckEchoCallBack extends CallBack[String] with EchoCallBack

