package modules

import scaldi.Module
import usecase.echo._
import contract.usecase._
import contract.usecase.echo._

/**
 * UseCaseModule.
 */
class UseCaseModule extends Module {
  bind[HealthCheckEchoUseCase] to new HealthCheckEchoUseCaseImpl
  bind[DebugEchoUseCase] to new DebugEchoUseCaseImpl
}
