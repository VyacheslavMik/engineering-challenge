(ns problem1)

(def invoice (clojure.edn/read-string (slurp "invoice.edn")))

(defn iva-19? [{:keys [taxable/taxes]}]
  (->> taxes
       (some (fn [{:tax/keys [category rate]}]
               (and (= category :iva)
                    (= rate 19))))))

(defn ret-fuent-1? [{:keys [retentionable/retentions]}]
  (->> retentions
       (some (fn [{:retention/keys [category rate]}]
               (and (= category :ret_fuente)
                    (= rate 1))))))

(defn filter-items [{:keys [invoice/items]}]
  (->> items
       (filter (fn [item]
                 (let [iva-19?      (iva-19? item)
                       ret-fuent-1? (ret-fuent-1? item)]
                   (or (and iva-19?       (not ret-fuent-1?))
                       (and (not iva-19?) ret-fuent-1?)))))))
