package contract.usecase.echo

import contract.{ PushPort, UseCase }

/**
 * EchoUseCase.
 */
trait EchoUseCase extends UseCase with PushPort[Unit, String]

trait DebugEchoUseCase extends EchoUseCase

trait HealthCheckEchoUseCase extends EchoUseCase