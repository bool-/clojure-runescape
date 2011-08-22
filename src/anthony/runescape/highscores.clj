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

(defn hs-to-map [hiscores]
  (let [keys ["rank" "level" "xp"]]
    (loop [skill-count 0
           skill-map {}]
      (if (== skill-count (count keys))
        skill-map
        (recur
          (inc skill-count)
          (assoc skill-map
            (keyword (get keys skill-count))
            (nth hiscores skill-count)))))))

(defn get-highscores [name]
  (let [url (URL. (str "http://hiscore.runescape.com/index_lite.ws?player=" name))
        reader (BufferedReader. (InputStreamReader. (. url openStream)))]
    (loop [line (. reader readLine)
           skill-count 0
           skill-map {}]
      (if (or (nil? line) (== skill-count (count skills)))
        skill-map
        (recur (. reader readLine)
          (inc skill-count)
          (assoc skill-map
            (keyword (get skills skill-count))
            (hs-to-map (str/split line #","))))))))


(pprint (get-highscores "Zezima"))
(comment ; output of the above statement
  {:smithing {:xp "35063667", :level "99", :rank "133"},
   :strength {:xp "43905430", :level "99", :rank "4765"},
   :magic {:xp "67737352", :level "99", :rank "95"},
   :crafting {:xp "118856020", :level "99", :rank "48"},
   :constitution {:xp "67405971", :level "99", :rank "808"},
   :ranged {:xp "37868023", :level "99", :rank "1423"},
   :agility {:xp "62825893", :level "99", :rank "47"},
   :summoning {:xp "17289173", :level "99", :rank "1114"},
   :firemaking {:xp "29720786", :level "99", :rank "1967"},
   :fishing {:xp "157284541", :level "99", :rank "76"},
   :dungeoneering {:xp "14098780", :level "99", :rank "5334"},
   :woodcutting {:xp "200000000", :level "99", :rank "5"},
   :herblore {:xp "17882416", :level "99", :rank "3438"},
   :fletching {:xp "25678279", :level "99", :rank "3327"},
   :thieving {:xp "14856933", :level "99", :rank "2463"},
   :prayer {:xp "14814116", :level "99", :rank "1097"},
   :slayer {:xp "200000000", :level "99", :rank "5"},
   :farming {:xp "16858935", :level "99", :rank "4006"},
   :hunter {:xp "31432600", :level "99", :rank "476"},
   :construction {:xp "13768254", :level "99", :rank "18000"},
   :mining {:xp "21136035", :level "99", :rank "565"},
   :defence {:xp "59876644", :level "99", :rank "853"},
   :overall {:xp "1496316186", :level "2496", :rank "27"},
   :attack {:xp "68486119", :level "99", :rank "1056"},
   :runecrafting {:xp "19452811", :level "99", :rank "3078"}})