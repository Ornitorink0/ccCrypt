/*
 * CCCrypt - Simple GUI Text Cryptor
 * By Ornitorink0
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://opensource.org/licenses/MIT
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * @author Ornitorink0
 * @since 1.0
 */

package util;

import java.time.LocalDate;
import java.time.temporal.ChronoField;

public class LocalData {
    // Metodo per ottenere il giorno dell'anno corrente
    public static int getCurrentDayOfYear() {
        return LocalDate.now().get(ChronoField.DAY_OF_YEAR);
    }
}
