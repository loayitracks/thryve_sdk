#
#  Be sure to run `pod spec lint ThryveCoreSDK.podspec' to ensure this is a
#  valid spec and to remove all comments including this before submitting the spec.
#
#  To learn more about Podspec attributes see https://guides.cocoapods.org/syntax/podspec.html
#  To see working Podspecs in the CocoaPods repo see https://github.com/CocoaPods/Specs/
#

Pod::Spec.new do |spec|

  spec.name = "CoreSDK"
  spec.version = "4.9.12"
  spec.summary = "Core module of the Thryve SDK for iOS"
  
  spec.description = <<-DESC
  A main SDK module in order to perform workflow with Thryve
  DESC
  
  spec.homepage = "https://thryve.health/api-doc/index.php"
  
  spec.license = { :type => "mHealth Pioneers GmbH", :text => "Copyright mHealth Pioneers GmbH. All rights reserved." }
  
  spec.author = { "Thryve" => "support@thryve.de" }

  spec.platform = :ios, "12.0"
  spec.ios.vendored_frameworks = 'CoreSDK.framework'
  spec.source = { :http => "https://thryve.health/files/cocoapods/wV7BPEm27aVsd67tCXE2fbMekmCGZq6r/CoreSDK.framework.zip" }
  spec.exclude_files = "Classes/Exclude"

end
