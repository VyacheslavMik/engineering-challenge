(ns filter-items)

(defn iva-19?
  "Checks if there is a tax with the category :iva and rate 19."
  [{:keys [taxable/taxes]}]
  (->> taxes
       (some (fn [{:tax/keys [category rate]}]
               (and (= category :iva)
                    (= rate 19))))))

(defn ret-fuent-1?
  "Checks if there is a retentation with the category :ret_fuente and rate 1."
  [{:keys [retentionable/retentions]}]
  (->> retentions
       (some (fn [{:retention/keys [category rate]}]
               (and (= category :ret_fuente)
                    (= rate 1))))))

(defn filter-items
  "Returns only those items that have either tax with category :iva and rate 19 or
  have retentation with the category :ret_fuente and rate 1. Accepts invoice."
  [{:keys [invoice/items]
    :as _invoice}]
  (->> items
       (filter (fn [item]
                 (let [iva-19?      (iva-19? item)
                       ret-fuent-1? (ret-fuent-1? item)]
                   (or (and iva-19?       (not ret-fuent-1?))
                       (and (not iva-19?) ret-fuent-1?)))))))
