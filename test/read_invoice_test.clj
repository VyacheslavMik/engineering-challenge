(ns read-invoice-test
  (:require [clojure.test :refer [deftest testing is]]
            [clojure.spec.alpha :as s]
            [invoice-spec :as invoice-spec]
            [read-invoice :as sut]))

(deftest read-invoice-test
  (testing "Read invoice test"
    (is (= {:invoice/issue-date #inst "2020-10-13"
	    :invoice/customer {:customer/name "ANDRADE RODRIGUEZ MANUEL ALEJANDRO"
	                       :customer/email "cgallegoaecu@gmail.com"}
	    :invoice/items [{:invoice-item/price 10000.0
	                     :invoice-item/quantity 1.0
	                     :invoice-item/sku "SUPER-1"
	                     :invoice-item/taxes [{:tax/category :iva
                                                   :tax/rate 5.0}]}

	                    {:invoice-item/price 20000.0
	                     :invoice-item/quantity 1.0
	                     :invoice-item/sku "SUPER-2"
	                     :invoice-item/taxes [{:tax/category :iva
                                                   :tax/rate 19.0}]}

	                    {:invoice-item/price 30000.0
	                     :invoice-item/quantity 1.0
	                     :invoice-item/sku "SUPER-3"
	                     :invoice-item/taxes [{:tax/category :iva
                                                   :tax/rate 19.0}]}]}
           (sut/read-invoice "invoice.json"))))

  (testing "Read invoice is valid"
    (is (s/valid? ::invoice-spec/invoice
                  (sut/read-invoice "invoice.json")))))
