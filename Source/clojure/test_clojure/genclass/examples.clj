;   Copyright (c) Rich Hickey. All rights reserved.
;   The use and distribution terms for this software are covered by the
;   Eclipse Public License 1.0 (http://opensource.org/licenses/eclipse-1.0.php)
;   which can be found in the file epl-v10.html at the root of this distribution.
;   By using this software in any fashion, you are agreeing to be bound by
;   the terms of this license.
;   You must not remove this notice, or any other, from this software.

(ns ^{:doc "Test classes that are AOT-compile for the tests in
           clojure.test-clojure.genclass."
      :author "Stuart Halloway, Daniel Solano Gómez"}
  clojure.test-clojure.genclass.examples)

(definterface ExampleInterface
  (foo [a])
  (foo [a b])
  (foo [a ^int b]))

(gen-class :name clojure.test_clojure.genclass.examples.ExampleClass
           :implements [clojure.test_clojure.genclass.examples.ExampleInterface])

;; -foo-Object unimplemented to test missing fn case

(defn -foo-Object-Object
  [_ o1 o2]
  "foo with o, o")

(defn -foo-Object-Int32               ;;; -foo-Object-Int32
  [_ o i]
  "foo with o, i")

#_(gen-class :name ^{Deprecated {}
                   SuppressWarnings ["Warning1"] ; discarded
                   java.lang.annotation.Target []}
                 clojure.test_clojure.genclass.examples.ExampleAnnotationClass
           :prefix "annot-"
           :methods [[^{Deprecated {}
                        Override {}} ;discarded
                      foo [^{java.lang.annotation.Retention java.lang.annotation.RetentionPolicy/SOURCE
                             java.lang.annotation.Target    [java.lang.annotation.ElementType/TYPE
                                                             java.lang.annotation.ElementType/PARAMETER]}
                           String] void]])

;;;(gen-class :name clojure.test_clojure.genclass.examples.ProtectedFinalTester
;;;           :extends java.lang.ClassLoader
;;;           :main false
;;;           :prefix "pf-"
;;;           :exposes-methods {findSystemClass superFindSystemClass})

(defn pf-findSystemClass
  "This function should never be called."
  [_ name]
  clojure.lang.RT)

(definterface ArrayDefInterface
  ; primitive array sugar
  (^void takesByteArray [^bytes a])    (^void takesSByteArray [^sbytes a])
  (^void takesCharArray [^chars a])
  (^void takesShortArray [^shorts a])  (^void takesUShortArray [^ushorts a])
  (^void takesIntArray [^ints a])      (^void takesUIntArray [^uints a])
  (^void takesLongArray [^longs a])    (^void takesULongArray [^ulongs a])
  (^void takesFloatArray [^floats a])
  (^void takesDoubleArray [^doubles a])
  (^void takesBooleanArray [^booleans a])
  ; raw primitive arrays
  (^"System.Byte[]"    returnsByteArray [])        (^"System.SByte[]"    returnsSByteArray [])      ;;; "[B"
  (^"System.Char[]"    returnsCharArray [])                                                         ;;; "[C"
  (^"System.Int32[]"   returnsIntArray [])         (^"System.UInt32[]"   returnsUIntArray [])       ;;; "[I"
  (^"System.Int16[]"   returnsShortArray [])       (^"System.UInt16[]"   returnsUShortArray [])     ;;; "[S"
  (^"System.Int64[]"   returnsLongArray [])        (^"System.UInt64[]"   returnsULongArray [])      ;;; "[J"
  (^"System.Single[]"  returnsFloatArray [])                                                        ;;; "[F"
  (^"System.Double[]"  returnsDoubleArray [])                                                       ;;; "[D"
  (^"System.Boolean[]" returnsBooleanArray []))                                                     ;;; "[Z"

(definterface UsesPreviousInterfaceFromThisFile
  (^clojure.test_clojure.genclass.examples.ArrayDefInterface
   identity
   [^clojure.test_clojure.genclass.examples.ArrayDefInterface a]))

(gen-interface
  :name clojure.test_clojure.genclass.examples.ArrayGenInterface
  :methods [; sugar
            [takesByteArray [bytes] void]                [takesSByteArray [sbytes] void]
            [takesCharArray [chars] void]
            [takesShortArray [shorts] void]              [takesUShortArray [ushorts] void]
            [takesIntArray [ints] void]                  [takesUIntArray [uints] void]
            [takesLongArray [longs] void]                [takesULongArray [ulongs] void]
            [takesFloatArray [floats] void]
            [takesDoubleArray [doubles] void]
            [takesBooleanArray [booleans] void]
            ; raw primitive types
            [returnsByteArray [] "System.Byte[]"]        [returnsSByteArray [] "System.SByte[]"]       ;;; "[B"]
            [returnsCharArray [] "System.Char[]"]                                                      ;;; "[C"]
            [returnsShortArray [] "System.Int16[]"]      [returnsUShortArray [] "System.UInt16[]"]     ;;; "[S"]
            [returnsIntArray [] "System.Int32[]"]        [returnsUIntArray [] "System.UInt32[]"]       ;;; "[I"]
            [returnsLongArray [] "System.Int64[]"]       [returnsULongArray [] "System.UInt64[]"]      ;;; "[J"]
            [returnsFloatArray [] "System.Single[]"]                                                   ;;; "[F"]
            [returnsDoubleArray [] "System.Double[]"]                                                  ;;; "[D"]
            [returnsBooleanArray [] "System.Boolean[]"]])                                              ;;; "[Z"]