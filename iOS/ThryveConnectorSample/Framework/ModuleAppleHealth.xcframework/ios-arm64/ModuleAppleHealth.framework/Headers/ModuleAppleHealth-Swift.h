#if 0
#elif defined(__arm64__) && __arm64__
// Generated by Apple Swift version 5.7 (swiftlang-5.7.0.127.4 clang-1400.0.29.50)
#ifndef MODULEAPPLEHEALTH_SWIFT_H
#define MODULEAPPLEHEALTH_SWIFT_H
#pragma clang diagnostic push
#pragma clang diagnostic ignored "-Wgcc-compat"

#if !defined(__has_include)
# define __has_include(x) 0
#endif
#if !defined(__has_attribute)
# define __has_attribute(x) 0
#endif
#if !defined(__has_feature)
# define __has_feature(x) 0
#endif
#if !defined(__has_warning)
# define __has_warning(x) 0
#endif

#if __has_include(<swift/objc-prologue.h>)
# include <swift/objc-prologue.h>
#endif

#pragma clang diagnostic ignored "-Wduplicate-method-match"
#pragma clang diagnostic ignored "-Wauto-import"
#if defined(__OBJC__)
#include <Foundation/Foundation.h>
#endif
#if defined(__cplusplus)
#include <cstdint>
#include <cstddef>
#include <cstdbool>
#else
#include <stdint.h>
#include <stddef.h>
#include <stdbool.h>
#endif

#if !defined(SWIFT_TYPEDEFS)
# define SWIFT_TYPEDEFS 1
# if __has_include(<uchar.h>)
#  include <uchar.h>
# elif !defined(__cplusplus)
typedef uint_least16_t char16_t;
typedef uint_least32_t char32_t;
# endif
typedef float swift_float2  __attribute__((__ext_vector_type__(2)));
typedef float swift_float3  __attribute__((__ext_vector_type__(3)));
typedef float swift_float4  __attribute__((__ext_vector_type__(4)));
typedef double swift_double2  __attribute__((__ext_vector_type__(2)));
typedef double swift_double3  __attribute__((__ext_vector_type__(3)));
typedef double swift_double4  __attribute__((__ext_vector_type__(4)));
typedef int swift_int2  __attribute__((__ext_vector_type__(2)));
typedef int swift_int3  __attribute__((__ext_vector_type__(3)));
typedef int swift_int4  __attribute__((__ext_vector_type__(4)));
typedef unsigned int swift_uint2  __attribute__((__ext_vector_type__(2)));
typedef unsigned int swift_uint3  __attribute__((__ext_vector_type__(3)));
typedef unsigned int swift_uint4  __attribute__((__ext_vector_type__(4)));
#endif

#if !defined(SWIFT_PASTE)
# define SWIFT_PASTE_HELPER(x, y) x##y
# define SWIFT_PASTE(x, y) SWIFT_PASTE_HELPER(x, y)
#endif
#if !defined(SWIFT_METATYPE)
# define SWIFT_METATYPE(X) Class
#endif
#if !defined(SWIFT_CLASS_PROPERTY)
# if __has_feature(objc_class_property)
#  define SWIFT_CLASS_PROPERTY(...) __VA_ARGS__
# else
#  define SWIFT_CLASS_PROPERTY(...)
# endif
#endif

#if __has_attribute(objc_runtime_name)
# define SWIFT_RUNTIME_NAME(X) __attribute__((objc_runtime_name(X)))
#else
# define SWIFT_RUNTIME_NAME(X)
#endif
#if __has_attribute(swift_name)
# define SWIFT_COMPILE_NAME(X) __attribute__((swift_name(X)))
#else
# define SWIFT_COMPILE_NAME(X)
#endif
#if __has_attribute(objc_method_family)
# define SWIFT_METHOD_FAMILY(X) __attribute__((objc_method_family(X)))
#else
# define SWIFT_METHOD_FAMILY(X)
#endif
#if __has_attribute(noescape)
# define SWIFT_NOESCAPE __attribute__((noescape))
#else
# define SWIFT_NOESCAPE
#endif
#if __has_attribute(ns_consumed)
# define SWIFT_RELEASES_ARGUMENT __attribute__((ns_consumed))
#else
# define SWIFT_RELEASES_ARGUMENT
#endif
#if __has_attribute(warn_unused_result)
# define SWIFT_WARN_UNUSED_RESULT __attribute__((warn_unused_result))
#else
# define SWIFT_WARN_UNUSED_RESULT
#endif
#if __has_attribute(noreturn)
# define SWIFT_NORETURN __attribute__((noreturn))
#else
# define SWIFT_NORETURN
#endif
#if !defined(SWIFT_CLASS_EXTRA)
# define SWIFT_CLASS_EXTRA
#endif
#if !defined(SWIFT_PROTOCOL_EXTRA)
# define SWIFT_PROTOCOL_EXTRA
#endif
#if !defined(SWIFT_ENUM_EXTRA)
# define SWIFT_ENUM_EXTRA
#endif
#if !defined(SWIFT_CLASS)
# if __has_attribute(objc_subclassing_restricted)
#  define SWIFT_CLASS(SWIFT_NAME) SWIFT_RUNTIME_NAME(SWIFT_NAME) __attribute__((objc_subclassing_restricted)) SWIFT_CLASS_EXTRA
#  define SWIFT_CLASS_NAMED(SWIFT_NAME) __attribute__((objc_subclassing_restricted)) SWIFT_COMPILE_NAME(SWIFT_NAME) SWIFT_CLASS_EXTRA
# else
#  define SWIFT_CLASS(SWIFT_NAME) SWIFT_RUNTIME_NAME(SWIFT_NAME) SWIFT_CLASS_EXTRA
#  define SWIFT_CLASS_NAMED(SWIFT_NAME) SWIFT_COMPILE_NAME(SWIFT_NAME) SWIFT_CLASS_EXTRA
# endif
#endif
#if !defined(SWIFT_RESILIENT_CLASS)
# if __has_attribute(objc_class_stub)
#  define SWIFT_RESILIENT_CLASS(SWIFT_NAME) SWIFT_CLASS(SWIFT_NAME) __attribute__((objc_class_stub))
#  define SWIFT_RESILIENT_CLASS_NAMED(SWIFT_NAME) __attribute__((objc_class_stub)) SWIFT_CLASS_NAMED(SWIFT_NAME)
# else
#  define SWIFT_RESILIENT_CLASS(SWIFT_NAME) SWIFT_CLASS(SWIFT_NAME)
#  define SWIFT_RESILIENT_CLASS_NAMED(SWIFT_NAME) SWIFT_CLASS_NAMED(SWIFT_NAME)
# endif
#endif

#if !defined(SWIFT_PROTOCOL)
# define SWIFT_PROTOCOL(SWIFT_NAME) SWIFT_RUNTIME_NAME(SWIFT_NAME) SWIFT_PROTOCOL_EXTRA
# define SWIFT_PROTOCOL_NAMED(SWIFT_NAME) SWIFT_COMPILE_NAME(SWIFT_NAME) SWIFT_PROTOCOL_EXTRA
#endif

#if !defined(SWIFT_EXTENSION)
# define SWIFT_EXTENSION(M) SWIFT_PASTE(M##_Swift_, __LINE__)
#endif

#if !defined(OBJC_DESIGNATED_INITIALIZER)
# if __has_attribute(objc_designated_initializer)
#  define OBJC_DESIGNATED_INITIALIZER __attribute__((objc_designated_initializer))
# else
#  define OBJC_DESIGNATED_INITIALIZER
# endif
#endif
#if !defined(SWIFT_ENUM_ATTR)
# if defined(__has_attribute) && __has_attribute(enum_extensibility)
#  define SWIFT_ENUM_ATTR(_extensibility) __attribute__((enum_extensibility(_extensibility)))
# else
#  define SWIFT_ENUM_ATTR(_extensibility)
# endif
#endif
#if !defined(SWIFT_ENUM)
# define SWIFT_ENUM(_type, _name, _extensibility) enum _name : _type _name; enum SWIFT_ENUM_ATTR(_extensibility) SWIFT_ENUM_EXTRA _name : _type
# if __has_feature(generalized_swift_name)
#  define SWIFT_ENUM_NAMED(_type, _name, SWIFT_NAME, _extensibility) enum _name : _type _name SWIFT_COMPILE_NAME(SWIFT_NAME); enum SWIFT_COMPILE_NAME(SWIFT_NAME) SWIFT_ENUM_ATTR(_extensibility) SWIFT_ENUM_EXTRA _name : _type
# else
#  define SWIFT_ENUM_NAMED(_type, _name, SWIFT_NAME, _extensibility) SWIFT_ENUM(_type, _name, _extensibility)
# endif
#endif
#if !defined(SWIFT_UNAVAILABLE)
# define SWIFT_UNAVAILABLE __attribute__((unavailable))
#endif
#if !defined(SWIFT_UNAVAILABLE_MSG)
# define SWIFT_UNAVAILABLE_MSG(msg) __attribute__((unavailable(msg)))
#endif
#if !defined(SWIFT_AVAILABILITY)
# define SWIFT_AVAILABILITY(plat, ...) __attribute__((availability(plat, __VA_ARGS__)))
#endif
#if !defined(SWIFT_WEAK_IMPORT)
# define SWIFT_WEAK_IMPORT __attribute__((weak_import))
#endif
#if !defined(SWIFT_DEPRECATED)
# define SWIFT_DEPRECATED __attribute__((deprecated))
#endif
#if !defined(SWIFT_DEPRECATED_MSG)
# define SWIFT_DEPRECATED_MSG(...) __attribute__((deprecated(__VA_ARGS__)))
#endif
#if __has_feature(attribute_diagnose_if_objc)
# define SWIFT_DEPRECATED_OBJC(Msg) __attribute__((diagnose_if(1, Msg, "warning")))
#else
# define SWIFT_DEPRECATED_OBJC(Msg) SWIFT_DEPRECATED_MSG(Msg)
#endif
#if defined(__OBJC__)
#if !defined(IBSegueAction)
# define IBSegueAction
#endif
#endif
#if !defined(SWIFT_EXTERN)
# if defined(__cplusplus)
#  define SWIFT_EXTERN extern "C"
# else
#  define SWIFT_EXTERN extern
# endif
#endif
#if !defined(SWIFT_CALL)
# define SWIFT_CALL __attribute__((swiftcall))
#endif
#if defined(__cplusplus)
#if !defined(SWIFT_NOEXCEPT)
# define SWIFT_NOEXCEPT noexcept
#endif
#else
#if !defined(SWIFT_NOEXCEPT)
# define SWIFT_NOEXCEPT 
#endif
#endif
#if defined(__cplusplus)
#if !defined(SWIFT_CXX_INT_DEFINED)
#define SWIFT_CXX_INT_DEFINED
namespace swift {
using Int = ptrdiff_t;
using UInt = size_t;
}
#endif
#endif
#if defined(__OBJC__)
#if __has_feature(modules)
#if __has_warning("-Watimport-in-framework-header")
#pragma clang diagnostic ignored "-Watimport-in-framework-header"
#endif
@import Foundation;
@import ObjectiveC;
#endif

#endif
#pragma clang diagnostic ignored "-Wproperty-attribute-mismatch"
#pragma clang diagnostic ignored "-Wduplicate-method-arg"
#if __has_warning("-Wpragma-clang-attribute")
# pragma clang diagnostic ignored "-Wpragma-clang-attribute"
#endif
#pragma clang diagnostic ignored "-Wunknown-pragmas"
#pragma clang diagnostic ignored "-Wnullability"
#pragma clang diagnostic ignored "-Wdollar-in-identifier-extension"

#if __has_attribute(external_source_symbol)
# pragma push_macro("any")
# undef any
# pragma clang attribute push(__attribute__((external_source_symbol(language="Swift", defined_in="ModuleAppleHealth",generated_declaration))), apply_to=any(function,enum,objc_interface,objc_category,objc_protocol))
# pragma pop_macro("any")
#endif

#if defined(__OBJC__)

/// HKConnector for integrating HealthKit
SWIFT_CLASS_NAMED("HKConnector")
@interface HKConnector : NSObject
/// Checks if Health Kit is supported by device
@property (nonatomic, readonly) BOOL isHealthKitAvailable;
/// Creates HKConnector instance.
/// requires:
/// uses accessToken to perform saving and uploading data routines
- (nonnull instancetype)init OBJC_DESIGNATED_INITIALIZER;
@end

/// Provides available services in HKConnector
typedef SWIFT_ENUM_NAMED(NSInteger, Service, "Service", open) {
  ServiceHealthKit = 4,
};


@class HKCharacteristicType;
@class HKSeriesType;
@class HKQuantityType;
@class HKCategoryType;
@class HKActivitySummaryType;
@class HKWorkoutType;
@class HKElectrocardiogramType;
@class HKObjectType;

/// All supported HealthKit data types by Thryve
SWIFT_CLASS_NAMED("HKConnectorType")
@interface HKConnectorType : NSObject
SWIFT_CLASS_PROPERTY(@property (nonatomic, class, readonly, strong) HKCharacteristicType * _Nonnull dateOfBirth;)
+ (HKCharacteristicType * _Nonnull)dateOfBirth SWIFT_WARN_UNUSED_RESULT;
SWIFT_CLASS_PROPERTY(@property (nonatomic, class, readonly, strong) HKCharacteristicType * _Nonnull bloodType;)
+ (HKCharacteristicType * _Nonnull)bloodType SWIFT_WARN_UNUSED_RESULT;
SWIFT_CLASS_PROPERTY(@property (nonatomic, class, readonly, strong) HKCharacteristicType * _Nonnull biologicalSex;)
+ (HKCharacteristicType * _Nonnull)biologicalSex SWIFT_WARN_UNUSED_RESULT;
SWIFT_CLASS_PROPERTY(@property (nonatomic, class, readonly, strong) HKSeriesType * _Nonnull heartbeatSeries SWIFT_AVAILABILITY(ios,introduced=13.0);)
+ (HKSeriesType * _Nonnull)heartbeatSeries SWIFT_WARN_UNUSED_RESULT;
SWIFT_CLASS_PROPERTY(@property (nonatomic, class, readonly, strong) HKQuantityType * _Nonnull heartRateVariabilitySDNN;)
+ (HKQuantityType * _Nonnull)heartRateVariabilitySDNN SWIFT_WARN_UNUSED_RESULT;
SWIFT_CLASS_PROPERTY(@property (nonatomic, class, readonly, strong) HKQuantityType * _Nonnull bodyMassIndex;)
+ (HKQuantityType * _Nonnull)bodyMassIndex SWIFT_WARN_UNUSED_RESULT;
SWIFT_CLASS_PROPERTY(@property (nonatomic, class, readonly, strong) HKQuantityType * _Nonnull bodyFatPercentage;)
+ (HKQuantityType * _Nonnull)bodyFatPercentage SWIFT_WARN_UNUSED_RESULT;
SWIFT_CLASS_PROPERTY(@property (nonatomic, class, readonly, strong) HKQuantityType * _Nonnull heartRate;)
+ (HKQuantityType * _Nonnull)heartRate SWIFT_WARN_UNUSED_RESULT;
SWIFT_CLASS_PROPERTY(@property (nonatomic, class, readonly, strong) HKQuantityType * _Nonnull respiratoryRate;)
+ (HKQuantityType * _Nonnull)respiratoryRate SWIFT_WARN_UNUSED_RESULT;
SWIFT_CLASS_PROPERTY(@property (nonatomic, class, readonly, strong) HKQuantityType * _Nonnull oxygenSaturation;)
+ (HKQuantityType * _Nonnull)oxygenSaturation SWIFT_WARN_UNUSED_RESULT;
SWIFT_CLASS_PROPERTY(@property (nonatomic, class, readonly, strong) HKQuantityType * _Nonnull bodyTemperature;)
+ (HKQuantityType * _Nonnull)bodyTemperature SWIFT_WARN_UNUSED_RESULT;
SWIFT_CLASS_PROPERTY(@property (nonatomic, class, readonly, strong) HKQuantityType * _Nonnull basalBodyTemperature;)
+ (HKQuantityType * _Nonnull)basalBodyTemperature SWIFT_WARN_UNUSED_RESULT;
SWIFT_CLASS_PROPERTY(@property (nonatomic, class, readonly, strong) HKQuantityType * _Nonnull bloodPressureSystolic;)
+ (HKQuantityType * _Nonnull)bloodPressureSystolic SWIFT_WARN_UNUSED_RESULT;
SWIFT_CLASS_PROPERTY(@property (nonatomic, class, readonly, strong) HKQuantityType * _Nonnull bloodPressureDiastolic;)
+ (HKQuantityType * _Nonnull)bloodPressureDiastolic SWIFT_WARN_UNUSED_RESULT;
SWIFT_CLASS_PROPERTY(@property (nonatomic, class, readonly, strong) HKQuantityType * _Nonnull bloodGlucose;)
+ (HKQuantityType * _Nonnull)bloodGlucose SWIFT_WARN_UNUSED_RESULT;
SWIFT_CLASS_PROPERTY(@property (nonatomic, class, readonly, strong) HKQuantityType * _Nonnull height;)
+ (HKQuantityType * _Nonnull)height SWIFT_WARN_UNUSED_RESULT;
SWIFT_CLASS_PROPERTY(@property (nonatomic, class, readonly, strong) HKQuantityType * _Nonnull bodyMass;)
+ (HKQuantityType * _Nonnull)bodyMass SWIFT_WARN_UNUSED_RESULT;
SWIFT_CLASS_PROPERTY(@property (nonatomic, class, readonly, strong) HKQuantityType * _Nonnull restingHeartRate;)
+ (HKQuantityType * _Nonnull)restingHeartRate SWIFT_WARN_UNUSED_RESULT;
SWIFT_CLASS_PROPERTY(@property (nonatomic, class, readonly, strong) HKQuantityType * _Nonnull vo2Max;)
+ (HKQuantityType * _Nonnull)vo2Max SWIFT_WARN_UNUSED_RESULT;
SWIFT_CLASS_PROPERTY(@property (nonatomic, class, readonly, strong) HKQuantityType * _Nonnull waistCircumference;)
+ (HKQuantityType * _Nonnull)waistCircumference SWIFT_WARN_UNUSED_RESULT;
SWIFT_CLASS_PROPERTY(@property (nonatomic, class, readonly, strong) HKQuantityType * _Nonnull stepCount;)
+ (HKQuantityType * _Nonnull)stepCount SWIFT_WARN_UNUSED_RESULT;
SWIFT_CLASS_PROPERTY(@property (nonatomic, class, readonly, strong) HKQuantityType * _Nonnull distanceSwimming;)
+ (HKQuantityType * _Nonnull)distanceSwimming SWIFT_WARN_UNUSED_RESULT;
SWIFT_CLASS_PROPERTY(@property (nonatomic, class, readonly, strong) HKQuantityType * _Nonnull distanceWalkingRunning;)
+ (HKQuantityType * _Nonnull)distanceWalkingRunning SWIFT_WARN_UNUSED_RESULT;
SWIFT_CLASS_PROPERTY(@property (nonatomic, class, readonly, strong) HKQuantityType * _Nonnull distanceCycling;)
+ (HKQuantityType * _Nonnull)distanceCycling SWIFT_WARN_UNUSED_RESULT;
SWIFT_CLASS_PROPERTY(@property (nonatomic, class, readonly, strong) HKQuantityType * _Nonnull basalEnergyBurned;)
+ (HKQuantityType * _Nonnull)basalEnergyBurned SWIFT_WARN_UNUSED_RESULT;
SWIFT_CLASS_PROPERTY(@property (nonatomic, class, readonly, strong) HKQuantityType * _Nonnull activeEnergyBurned;)
+ (HKQuantityType * _Nonnull)activeEnergyBurned SWIFT_WARN_UNUSED_RESULT;
SWIFT_CLASS_PROPERTY(@property (nonatomic, class, readonly, strong) HKQuantityType * _Nonnull flightsClimbed;)
+ (HKQuantityType * _Nonnull)flightsClimbed SWIFT_WARN_UNUSED_RESULT;
SWIFT_CLASS_PROPERTY(@property (nonatomic, class, readonly, strong) HKQuantityType * _Nonnull appleExerciseTime;)
+ (HKQuantityType * _Nonnull)appleExerciseTime SWIFT_WARN_UNUSED_RESULT;
SWIFT_CLASS_PROPERTY(@property (nonatomic, class, readonly, strong) HKQuantityType * _Nonnull dietaryEnergyConsumed;)
+ (HKQuantityType * _Nonnull)dietaryEnergyConsumed SWIFT_WARN_UNUSED_RESULT;
SWIFT_CLASS_PROPERTY(@property (nonatomic, class, readonly, strong) HKQuantityType * _Nonnull dietaryCarbohydrates;)
+ (HKQuantityType * _Nonnull)dietaryCarbohydrates SWIFT_WARN_UNUSED_RESULT;
SWIFT_CLASS_PROPERTY(@property (nonatomic, class, readonly, strong) HKQuantityType * _Nonnull dietaryFiber;)
+ (HKQuantityType * _Nonnull)dietaryFiber SWIFT_WARN_UNUSED_RESULT;
SWIFT_CLASS_PROPERTY(@property (nonatomic, class, readonly, strong) HKQuantityType * _Nonnull dietarySugar;)
+ (HKQuantityType * _Nonnull)dietarySugar SWIFT_WARN_UNUSED_RESULT;
SWIFT_CLASS_PROPERTY(@property (nonatomic, class, readonly, strong) HKQuantityType * _Nonnull dietaryFatTotal;)
+ (HKQuantityType * _Nonnull)dietaryFatTotal SWIFT_WARN_UNUSED_RESULT;
SWIFT_CLASS_PROPERTY(@property (nonatomic, class, readonly, strong) HKQuantityType * _Nonnull dietaryFatSaturated;)
+ (HKQuantityType * _Nonnull)dietaryFatSaturated SWIFT_WARN_UNUSED_RESULT;
SWIFT_CLASS_PROPERTY(@property (nonatomic, class, readonly, strong) HKQuantityType * _Nonnull dietaryProtein;)
+ (HKQuantityType * _Nonnull)dietaryProtein SWIFT_WARN_UNUSED_RESULT;
SWIFT_CLASS_PROPERTY(@property (nonatomic, class, readonly, strong) HKQuantityType * _Nonnull dietaryVitaminA;)
+ (HKQuantityType * _Nonnull)dietaryVitaminA SWIFT_WARN_UNUSED_RESULT;
SWIFT_CLASS_PROPERTY(@property (nonatomic, class, readonly, strong) HKQuantityType * _Nonnull dietaryThiamin;)
+ (HKQuantityType * _Nonnull)dietaryThiamin SWIFT_WARN_UNUSED_RESULT;
SWIFT_CLASS_PROPERTY(@property (nonatomic, class, readonly, strong) HKQuantityType * _Nonnull dietaryRiboflavin;)
+ (HKQuantityType * _Nonnull)dietaryRiboflavin SWIFT_WARN_UNUSED_RESULT;
SWIFT_CLASS_PROPERTY(@property (nonatomic, class, readonly, strong) HKQuantityType * _Nonnull dietaryNiacin;)
+ (HKQuantityType * _Nonnull)dietaryNiacin SWIFT_WARN_UNUSED_RESULT;
SWIFT_CLASS_PROPERTY(@property (nonatomic, class, readonly, strong) HKQuantityType * _Nonnull dietaryPantothenicAcid;)
+ (HKQuantityType * _Nonnull)dietaryPantothenicAcid SWIFT_WARN_UNUSED_RESULT;
SWIFT_CLASS_PROPERTY(@property (nonatomic, class, readonly, strong) HKQuantityType * _Nonnull dietaryVitaminB6;)
+ (HKQuantityType * _Nonnull)dietaryVitaminB6 SWIFT_WARN_UNUSED_RESULT;
SWIFT_CLASS_PROPERTY(@property (nonatomic, class, readonly, strong) HKQuantityType * _Nonnull dietaryVitaminB12;)
+ (HKQuantityType * _Nonnull)dietaryVitaminB12 SWIFT_WARN_UNUSED_RESULT;
SWIFT_CLASS_PROPERTY(@property (nonatomic, class, readonly, strong) HKQuantityType * _Nonnull dietaryVitaminC;)
+ (HKQuantityType * _Nonnull)dietaryVitaminC SWIFT_WARN_UNUSED_RESULT;
SWIFT_CLASS_PROPERTY(@property (nonatomic, class, readonly, strong) HKQuantityType * _Nonnull dietaryVitaminD;)
+ (HKQuantityType * _Nonnull)dietaryVitaminD SWIFT_WARN_UNUSED_RESULT;
SWIFT_CLASS_PROPERTY(@property (nonatomic, class, readonly, strong) HKQuantityType * _Nonnull dietaryVitaminE;)
+ (HKQuantityType * _Nonnull)dietaryVitaminE SWIFT_WARN_UNUSED_RESULT;
SWIFT_CLASS_PROPERTY(@property (nonatomic, class, readonly, strong) HKQuantityType * _Nonnull dietaryVitaminK;)
+ (HKQuantityType * _Nonnull)dietaryVitaminK SWIFT_WARN_UNUSED_RESULT;
SWIFT_CLASS_PROPERTY(@property (nonatomic, class, readonly, strong) HKQuantityType * _Nonnull dietaryFolate;)
+ (HKQuantityType * _Nonnull)dietaryFolate SWIFT_WARN_UNUSED_RESULT;
SWIFT_CLASS_PROPERTY(@property (nonatomic, class, readonly, strong) HKQuantityType * _Nonnull dietaryCalcium;)
+ (HKQuantityType * _Nonnull)dietaryCalcium SWIFT_WARN_UNUSED_RESULT;
SWIFT_CLASS_PROPERTY(@property (nonatomic, class, readonly, strong) HKQuantityType * _Nonnull dietaryIron;)
+ (HKQuantityType * _Nonnull)dietaryIron SWIFT_WARN_UNUSED_RESULT;
SWIFT_CLASS_PROPERTY(@property (nonatomic, class, readonly, strong) HKQuantityType * _Nonnull dietaryMagnesium;)
+ (HKQuantityType * _Nonnull)dietaryMagnesium SWIFT_WARN_UNUSED_RESULT;
SWIFT_CLASS_PROPERTY(@property (nonatomic, class, readonly, strong) HKQuantityType * _Nonnull dietaryPhosphorus;)
+ (HKQuantityType * _Nonnull)dietaryPhosphorus SWIFT_WARN_UNUSED_RESULT;
SWIFT_CLASS_PROPERTY(@property (nonatomic, class, readonly, strong) HKQuantityType * _Nonnull dietaryPotassium;)
+ (HKQuantityType * _Nonnull)dietaryPotassium SWIFT_WARN_UNUSED_RESULT;
SWIFT_CLASS_PROPERTY(@property (nonatomic, class, readonly, strong) HKQuantityType * _Nonnull dietarySodium;)
+ (HKQuantityType * _Nonnull)dietarySodium SWIFT_WARN_UNUSED_RESULT;
SWIFT_CLASS_PROPERTY(@property (nonatomic, class, readonly, strong) HKQuantityType * _Nonnull dietaryZinc;)
+ (HKQuantityType * _Nonnull)dietaryZinc SWIFT_WARN_UNUSED_RESULT;
SWIFT_CLASS_PROPERTY(@property (nonatomic, class, readonly, strong) HKQuantityType * _Nonnull dietaryIodine;)
+ (HKQuantityType * _Nonnull)dietaryIodine SWIFT_WARN_UNUSED_RESULT;
SWIFT_CLASS_PROPERTY(@property (nonatomic, class, readonly, strong) HKQuantityType * _Nonnull dietaryManganese;)
+ (HKQuantityType * _Nonnull)dietaryManganese SWIFT_WARN_UNUSED_RESULT;
SWIFT_CLASS_PROPERTY(@property (nonatomic, class, readonly, strong) HKQuantityType * _Nonnull electrodermalActivity;)
+ (HKQuantityType * _Nonnull)electrodermalActivity SWIFT_WARN_UNUSED_RESULT;
SWIFT_CLASS_PROPERTY(@property (nonatomic, class, readonly, strong) HKQuantityType * _Nonnull dietaryWater;)
+ (HKQuantityType * _Nonnull)dietaryWater SWIFT_WARN_UNUSED_RESULT;
SWIFT_CLASS_PROPERTY(@property (nonatomic, class, readonly, strong) HKQuantityType * _Nonnull environmentalAudioExposure SWIFT_AVAILABILITY(ios,introduced=13.0);)
+ (HKQuantityType * _Nonnull)environmentalAudioExposure SWIFT_WARN_UNUSED_RESULT;
SWIFT_CLASS_PROPERTY(@property (nonatomic, class, readonly, strong) HKQuantityType * _Nonnull headphoneAudioExposure SWIFT_AVAILABILITY(ios,introduced=13.0);)
+ (HKQuantityType * _Nonnull)headphoneAudioExposure SWIFT_WARN_UNUSED_RESULT;
SWIFT_CLASS_PROPERTY(@property (nonatomic, class, readonly, strong) HKCategoryType * _Nonnull sleepAnalysis;)
+ (HKCategoryType * _Nonnull)sleepAnalysis SWIFT_WARN_UNUSED_RESULT;
SWIFT_CLASS_PROPERTY(@property (nonatomic, class, readonly, strong) HKCategoryType * _Nonnull mindfulSession;)
+ (HKCategoryType * _Nonnull)mindfulSession SWIFT_WARN_UNUSED_RESULT;
SWIFT_CLASS_PROPERTY(@property (nonatomic, class, readonly, strong) HKCategoryType * _Nonnull appleStandHour;)
+ (HKCategoryType * _Nonnull)appleStandHour SWIFT_WARN_UNUSED_RESULT;
SWIFT_CLASS_PROPERTY(@property (nonatomic, class, readonly, strong) HKCategoryType * _Nonnull sexualActivity;)
+ (HKCategoryType * _Nonnull)sexualActivity SWIFT_WARN_UNUSED_RESULT;
SWIFT_CLASS_PROPERTY(@property (nonatomic, class, readonly, strong) HKCategoryType * _Nonnull intermenstrualBleeding;)
+ (HKCategoryType * _Nonnull)intermenstrualBleeding SWIFT_WARN_UNUSED_RESULT;
SWIFT_CLASS_PROPERTY(@property (nonatomic, class, readonly, strong) HKCategoryType * _Nonnull menstrualFlow;)
+ (HKCategoryType * _Nonnull)menstrualFlow SWIFT_WARN_UNUSED_RESULT;
SWIFT_CLASS_PROPERTY(@property (nonatomic, class, readonly, strong) HKCategoryType * _Nonnull ovulationTestResult;)
+ (HKCategoryType * _Nonnull)ovulationTestResult SWIFT_WARN_UNUSED_RESULT;
SWIFT_CLASS_PROPERTY(@property (nonatomic, class, readonly, strong) HKCategoryType * _Nonnull cervicalMucusQuality;)
+ (HKCategoryType * _Nonnull)cervicalMucusQuality SWIFT_WARN_UNUSED_RESULT;
SWIFT_CLASS_PROPERTY(@property (nonatomic, class, readonly, strong) HKCategoryType * _Nonnull audioExposureEvent SWIFT_AVAILABILITY(ios,introduced=13.0);)
+ (HKCategoryType * _Nonnull)audioExposureEvent SWIFT_WARN_UNUSED_RESULT;
SWIFT_CLASS_PROPERTY(@property (nonatomic, class, readonly, strong) HKActivitySummaryType * _Nonnull activitySummary;)
+ (HKActivitySummaryType * _Nonnull)activitySummary SWIFT_WARN_UNUSED_RESULT;
SWIFT_CLASS_PROPERTY(@property (nonatomic, class, readonly, strong) HKWorkoutType * _Nonnull workoutType;)
+ (HKWorkoutType * _Nonnull)workoutType SWIFT_WARN_UNUSED_RESULT;
SWIFT_CLASS_PROPERTY(@property (nonatomic, class, readonly, strong) HKElectrocardiogramType * _Nonnull electrocardiogramType SWIFT_AVAILABILITY(ios,introduced=14.0);)
+ (HKElectrocardiogramType * _Nonnull)electrocardiogramType SWIFT_WARN_UNUSED_RESULT;
SWIFT_CLASS_PROPERTY(@property (nonatomic, class, readonly, strong) HKCategoryType * _Nonnull rapidPoundingOrFlutteringHeartbeat SWIFT_AVAILABILITY(ios,introduced=13.6);)
+ (HKCategoryType * _Nonnull)rapidPoundingOrFlutteringHeartbeat SWIFT_WARN_UNUSED_RESULT;
SWIFT_CLASS_PROPERTY(@property (nonatomic, class, readonly, strong) HKCategoryType * _Nonnull skippedHeartbeat SWIFT_AVAILABILITY(ios,introduced=13.6);)
+ (HKCategoryType * _Nonnull)skippedHeartbeat SWIFT_WARN_UNUSED_RESULT;
SWIFT_CLASS_PROPERTY(@property (nonatomic, class, readonly, strong) HKCategoryType * _Nonnull fatigue SWIFT_AVAILABILITY(ios,introduced=13.6);)
+ (HKCategoryType * _Nonnull)fatigue SWIFT_WARN_UNUSED_RESULT;
SWIFT_CLASS_PROPERTY(@property (nonatomic, class, readonly, strong) HKCategoryType * _Nonnull shortnessOfBreath SWIFT_AVAILABILITY(ios,introduced=13.6);)
+ (HKCategoryType * _Nonnull)shortnessOfBreath SWIFT_WARN_UNUSED_RESULT;
SWIFT_CLASS_PROPERTY(@property (nonatomic, class, readonly, strong) HKCategoryType * _Nonnull chestTightnessOrPain SWIFT_AVAILABILITY(ios,introduced=13.6);)
+ (HKCategoryType * _Nonnull)chestTightnessOrPain SWIFT_WARN_UNUSED_RESULT;
SWIFT_CLASS_PROPERTY(@property (nonatomic, class, readonly, strong) HKCategoryType * _Nonnull fainting SWIFT_AVAILABILITY(ios,introduced=13.6);)
+ (HKCategoryType * _Nonnull)fainting SWIFT_WARN_UNUSED_RESULT;
SWIFT_CLASS_PROPERTY(@property (nonatomic, class, readonly, strong) HKCategoryType * _Nonnull dizziness SWIFT_AVAILABILITY(ios,introduced=13.6);)
+ (HKCategoryType * _Nonnull)dizziness SWIFT_WARN_UNUSED_RESULT;
SWIFT_CLASS_PROPERTY(@property (nonatomic, class, readonly, copy) NSSet<HKObjectType *> * _Nonnull allTypes;)
+ (NSSet<HKObjectType *> * _Nonnull)allTypes SWIFT_WARN_UNUSED_RESULT;
- (nonnull instancetype)init OBJC_DESIGNATED_INITIALIZER;
@end


/// Helper for reading and writing to HealthKit.
SWIFT_CLASS("_TtC17ModuleAppleHealth16HealthKitManager")
@interface HealthKitManager : NSObject
- (nonnull instancetype)init OBJC_DESIGNATED_INITIALIZER;
@end


#endif
#if defined(__cplusplus)
#endif
#if __has_attribute(external_source_symbol)
# pragma clang attribute pop
#endif
#pragma clang diagnostic pop
#endif

#else
#error unsupported Swift architecture
#endif