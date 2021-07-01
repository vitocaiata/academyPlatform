Feature: ebay test

  Rule: only the test on Ebay.com
  Background:
    Given the user start driver

    Scenario:
     When the user search item iphone
     Then close driver