/*
 * CCCrypt - Simple GUI Text Cryptor
 * By Matteo Gurrieri
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
 * @author Matteo Gurrieri
 * @since 1.0
 */

package util;

public class Algo {
    // Metodo per criptare il messaggio cifrato
    public static String encryptMessage(String msg) {
        StringBuilder encryptedMessage = new StringBuilder();

        // Otteniamo il giorno dell'anno
        int dayOfYear = LocalData.getCurrentDayOfYear();

        // Iteriamo ogni carattere del messaggio
        for (int i = 0; i < msg.length(); i++) {
            char msgChar = msg.charAt(i);

            // Creiamo una chiave dinamica basata sul giorno dell'anno
            int dynamicKey = (dayOfYear + i) % 95; // 95 è il numero di caratteri stampabili tra 32 e 126

            // Crittografiamo il carattere, mappando il risultato nell'intervallo stampabile
            // (32-126)
            char encryptedChar = (char) (((msgChar - 32 + dynamicKey) % 95) + 32);

            // Aggiungiamo il carattere cifrato al risultato
            encryptedMessage.append(encryptedChar);
        }

        return encryptedMessage.toString();
    }

    // Metodo per decriptare il messaggio cifrato
    public static String decryptMessage(String encryptedMsg) {
        StringBuilder decryptedMessage = new StringBuilder();

        // Otteniamo il giorno dell'anno
        int dayOfYear = LocalData.getCurrentDayOfYear();

        // Iteriamo ogni carattere del messaggio cifrato
        for (int i = 0; i < encryptedMsg.length(); i++) {
            char encryptedChar = encryptedMsg.charAt(i);

            // Creiamo una chiave dinamica basata sul giorno dell'anno
            int dynamicKey = (dayOfYear + i) % 95; // 95 è il numero di caratteri stampabili tra 32 e 126

            // Decriptiamo il carattere, riportandolo nell'intervallo stampabile (32-126)
            char decryptedChar = (char) ((((encryptedChar - 32 - dynamicKey) + 95) % 95) + 32);

            // Aggiungiamo il carattere decriptato al risultato
            decryptedMessage.append(decryptedChar);
        }

        return decryptedMessage.toString();
    }
}
