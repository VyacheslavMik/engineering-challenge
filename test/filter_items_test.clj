(ns filter-items-test
  (:require [clojure.test :refer [deftest testing is]]
            [filter-items :as sut]))

(deftest filter-items-test
  (testing "Filter items test"
    (is (= [{:invoice-item/id "ii3"
	     :invoice-item/sku "SKU 3"
	     :taxable/taxes [{:tax/id "t3"
                              :tax/category :iva
                              :tax/rate 19}]}

	    {:invoice-item/id "ii4"
	     :invoice-item/sku "SKU 3"
	     :retentionable/retentions [{:retention/id "r2"
                                         :retention/category :ret_fuente
                                         :retention/rate 1}]}]
           (sut/filter-items
            (clojure.edn/read-string
             (slurp "invoice.edn")))))))
