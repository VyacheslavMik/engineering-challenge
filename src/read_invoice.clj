(ns read-invoice
  (:require [clojure.data.json :as json])
  (:import (java.text SimpleDateFormat)
           (java.util TimeZone)))

(def parser "Parser for the clojure instant from the string. Eg, 13/10/2020."
  (doto (SimpleDateFormat. "dd/MM/yyyy")
    (.setTimeZone (TimeZone/getTimeZone "UTC"))))

(defn make-inst
  "Parses `string` into clojure instant."
  [string]
  (.parse parser string))

(defn make-tax
  "Converts a tax from json to the valid tax."
  [{:keys [tax_category tax_rate]
    :as _tax}]
  (assert (= tax_category "IVA"))
  {:tax/category :iva
   :tax/rate (double tax_rate)})

(defn make-invoice-item
  "Converts an invoice-item from json to the valid invoice-item."
  [{:keys [price quantity sku taxes]
    :as _invoice-item}]
  {:invoice-item/price price
   :invoice-item/quantity quantity
   :invoice-item/sku sku
   :invoice-item/taxes (mapv make-tax taxes)})

(defn read-invoice
  "Reads invoice from a json that resides under `path` and converts it
  to valid invoice."
  [path]
  (let [{:keys [invoice]} (json/read-json (slurp path))
        {:keys [issue_date
                customer
                items]} invoice
        {:keys [company_name email]} customer]
    {:invoice/issue-date (make-inst issue_date)
     :invoice/customer {:customer/name company_name
                        :customer/email email}
     :invoice/items (mapv make-invoice-item items)}))
