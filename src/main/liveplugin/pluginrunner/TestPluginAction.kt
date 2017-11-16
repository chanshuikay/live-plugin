/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package liveplugin.pluginrunner

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.DumbAware
import liveplugin.Icons
import liveplugin.IdeUtil
import liveplugin.pluginrunner.GroovyPluginRunner.Companion.testScript

class TestPluginAction: AnAction("Run Plugin Tests", "Run Plugin Integration Tests", Icons.testPluginIcon), DumbAware {

    override fun actionPerformed(event: AnActionEvent) {
        IdeUtil.saveAllFiles()
        val errorReporter = ErrorReporter()
        runPlugins(event.selectedFiles(), event, errorReporter, createPluginRunners(errorReporter))
    }

    override fun update(event: AnActionEvent) {
        event.presentation.isEnabled = event.selectedFiles().any { pluginFolder(it) != null }
    }

    private fun createPluginRunners(errorReporter: ErrorReporter) =
        listOf(GroovyPluginRunner(testScript, errorReporter, environment()))
}
