(ns problem2
  (:require [clojure.data.json :as json]
            [clojure.spec.alpha :as s]
            [invoice-spec :as invoice-spec])
  (:import (java.text SimpleDateFormat)))

(def parser (SimpleDateFormat. "dd/MM/yyyy"))

(defn make-inst [string]
  (.parse parser string))

(defn make-tax [{:keys [tax_category tax_rate]}]
  (assert (= tax_category "IVA"))
  {:tax/category :iva
   :tax/rate (double tax_rate)})

(defn make-invoice-item [{:keys [price quantity sku taxes]}]
  {:invoice-item/price price
   :invoice-item/quantity quantity
   :invoice-item/sku sku
   :invoice-item/taxes (mapv make-tax taxes)})

(defn read-invoice [path]
  (let [{:keys [invoice]} (json/read-json (slurp path))
        {:keys [issue_date
                customer
                items]} invoice
        {:keys [company_name email]} customer]
    {:invoice/issue-date (make-inst issue_date)
     :invoice/customer {:customer/name company_name
                        :customer/email email}
     :invoice/items (mapv make-invoice-item items)}))

(s/valid? ::invoice-spec/invoice
          (read-invoice "invoice.json"))
