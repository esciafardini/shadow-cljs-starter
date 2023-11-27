(ns main.core (:require
               [reagent.core :as r]
               [reagent.dom :as rdom]))

(defonce app-state (r/atom {:title "WhichWeather"
                            :postal-code ""
                            :temperatures {:today {:label "Today"
                                                   :value nil}
                                           :tomorrow {:label "Tomorrow"
                                                      :value nil}}}))

(defn title []
  [:h1 (:title @app-state)])

(defn temperature [temp]
  [:div.temperature
   [:div.value
    (:value temp)]
   [:h2 (:label temp)]])

(defn postal-code []
  [:div.postal-code
   [:h3 "Enter your postal code"]
   [:input {:type "number"
            :placeholder "Postal Code"
            :value (:postal-code @app-state)
            :on-change #(swap! app-state assoc :postal-code (-> % .-target .-value))}]
   [:button "Go"]])

(defn app []
  [:div.app
   [:p (:postal-code @app-state)]
   [title]
   [:div.temperatures
    (for [temp (vals (:temperatures @app-state))]
      ^{:key temp}
      [temperature temp])]
   [postal-code]])

(defn ^:export ^:dev/after-load init []
  (let [app-node (.getElementById js/document "app")]
    (rdom/render [app] app-node)))
