(ns invoice-item-test
  (:require [clojure.test :refer [deftest testing is]]
            [invoice-item :as sut]))

(deftest subtotal-test
  (testing "Regular invoice item"
    (is (= 94.5 (sut/subtotal {:invoice-item/precise-quantity 7
                               :invoice-item/precise-price 15.0
                               :invoice-item/discount-rate 10}))))

  (testing "Wrong discount rate"
    (is (= -420.0 (sut/subtotal {:invoice-item/precise-quantity 7
                                 :invoice-item/precise-price 15.0
                                 :invoice-item/discount-rate 500}))))

  (testing "Wrong missing discount rate"
    (is (= 105.0 (sut/subtotal {:invoice-item/precise-quantity 7
                                :invoice-item/precise-price 15.0}))))

  (testing "Wrong missing price"
    (is (thrown? NullPointerException (sut/subtotal {:invoice-item/precise-quantity 7
                                                     :invoice-item/discount-rate 10}))))

  (testing "Wrong missing quantity"
    (is (thrown? NullPointerException (sut/subtotal {:invoice-item/precise-price 15.0
                                                     :invoice-item/discount-rate 10}))))

  (testing "Empty invoice item"
    (is (thrown? NullPointerException (sut/subtotal {}))))

  (testing "Nil invoice item"
    (is (thrown? NullPointerException (sut/subtotal nil))))

  (testing "Zero quantiy"
    (is (= 0.0 (sut/subtotal {:invoice-item/precise-quantity 0
                              :invoice-item/precise-price 15.0
                              :invoice-item/discount-rate 500}))))

  (testing "Zero price"
    (is (= 0.0 (sut/subtotal {:invoice-item/precise-quantity 7
                              :invoice-item/precise-price 0
                              :invoice-item/discount-rate 500})))))
