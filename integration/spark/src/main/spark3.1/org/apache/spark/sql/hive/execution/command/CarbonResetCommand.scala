/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.spark.sql.hive.execution.command

import org.apache.spark.sql.{CarbonEnv, Row, SparkSession}
import org.apache.spark.sql.catalyst.plans.logical.LogicalPlan
import org.apache.spark.sql.execution.command.{ResetCommand, RunnableCommand}

case class CarbonResetCommand()
  extends RunnableCommand {
  override val output = Seq()

  override def run(sparkSession: SparkSession): Seq[Row] = {
    CarbonEnv.getInstance(sparkSession).carbonSessionInfo.getSessionParams.clear()
    ResetCommand(None).run(sparkSession)
  }
}

/**
 * This method matches the reset command based on the spark version
 */
object MatchResetCommand {
  def unapply(plan: LogicalPlan): Option[LogicalPlan] = {
    plan match {
      case _: ResetCommand =>
        Some(plan)
      case _ =>
        None
    }
  }
}
