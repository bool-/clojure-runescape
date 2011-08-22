(ns anthony.runescape.highscores
  (import (java.io BufferedReader InputStreamReader)
    (java.net URL))
  (:require [clojure.string :as str])
  (:use [clojure.pprint]))

(def skills ["overall" "attack" "defence", "strength" "constitution" "ranged"
             "prayer" "magic" "woodcutting" "fletching" "fishing" "firemaking"
             "crafting" "smithing" "mining" "herblore" "agility" "thieving"
             "slayer" "runecrafting" "hunter" "farming" "construction"
             "summoning" "dungeoneering"])

(defn get-highscores [name]
  (let [url (URL. (str "http://hiscore.runescape.com/index_lite.ws?player=" name))
        reader (BufferedReader. (InputStreamReader. (. url openStream)))]
    (loop [line (. reader readLine)
           skill-count 0
           skill-map {}]
      (if (or (nil? line) (== skill-count (count skills)))
        skill-map
        (recur (. reader readLine) (inc skill-count) (assoc skill-map (keyword (get skills skill-count)) (vec (str/split line #","))))))))


(pprint (get-highscores "Zezima"))
(comment ; output of the above statement
  {:smithing ["133" "99" "35063667"],
 :strength ["4765" "99" "43905430"],
 :magic ["95" "99" "67737352"],
 :crafting ["48" "99" "118856020"],
 :constitution ["808" "99" "67405971"],
 :ranged ["1423" "99" "37868023"],
 :agility ["47" "99" "62825893"],
 :summoning ["1114" "99" "17289173"],
 :firemaking ["1967" "99" "29720786"],
 :fishing ["76" "99" "157284541"],
 :dungeoneering ["5334" "99" "14098780"],
 :woodcutting ["5" "99" "200000000"],
 :herblore ["3438" "99" "17882416"],
 :fletching ["3327" "99" "25678279"],
 :thieving ["2463" "99" "14856933"],
 :prayer ["1097" "99" "14814116"],
 :slayer ["5" "99" "200000000"],
 :farming ["4006" "99" "16858935"],
 :hunter ["476" "99" "31432600"],
 :construction ["17999" "99" "13768254"],
 :mining ["565" "99" "21136035"],
 :defence ["853" "99" "59876644"],
 :overall ["27" "2496" "1496316186"],
 :attack ["1056" "99" "68486119"],
 :runecrafting ["3078" "99" "19452811"]})