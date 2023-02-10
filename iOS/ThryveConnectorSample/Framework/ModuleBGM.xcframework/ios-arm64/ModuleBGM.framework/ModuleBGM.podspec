#
#  Be sure to run `pod spec lint ModuleBGM.podspec' to ensure this is a
#  valid spec and to remove all comments including this before submitting the spec.
#
#  To learn more about Podspec attributes see https://guides.cocoapods.org/syntax/podspec.html
#  To see working Podspecs in the CocoaPods repo see https://github.com/CocoaPods/Specs/
#

Pod::Spec.new do |spec|

  spec.name = "ModuleBGM"
  spec.version = "4.9.12"
  spec.summary = "Blood glucose measurement module of the Thryve SDK for iOS"
  
  spec.description = <<-DESC
  A module in order to perform workflow for glucose data extraction of Thryve SDK
  DESC
  
  spec.homepage = "https://thryve.health/api-doc/index.php"
  
  spec.license = { :type => "mHealth Pioneers GmbH", :text => "Copyright mHealth Pioneers GmbH. All rights reserved." }
  
  spec.author = { "Thryve" => "support@thryve.de" }

  spec.platform = :ios, "12.0"
  spec.ios.vendored_frameworks = 'ModuleBGM.framework'
  spec.source = { :http => "https://thryve.health/files/cocoapods/wV7BPEm27aVsd67tCXE2fbMekmCGZq6r/ModuleBGM.framework.zip" }
  spec.exclude_files = "Classes/Exclude"

end
