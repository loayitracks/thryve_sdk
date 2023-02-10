//
//  RecognitionView.swift
//  SampleCore
//
//  Created by Florian on 20.11.19.
//  Copyright © 2019 mHealthPioneers. All rights reserved.
//

import SwiftUI

struct RecognitionView: View, ViewProtocol {
    @State var isLocationTrackingToggled: Bool = false

    @ObservedObject var viewModel: RecognitionViewModel

    var body: some View {
        NavigationView {
            VStack(spacing: 16.0) {
                Image("mainLogo")
                Spacer()
                Toggle(isOn: $viewModel.isStepsOn) {
                    Text("Step detection")
                }
                Toggle(isOn: $viewModel.isActivityOn) {
                    Text("Activity recognition")
                }
                Toggle(isOn: $viewModel.isWellbeingOn) {
                    Text("Wellbeing integration")
                }
                Button(action: {
                    self.isLocationTrackingToggled = !self.isLocationTrackingToggled
                }, label: {
                    Text("Location tracking")
                })
                .modifier(ButtonModifier())
                Spacer()
                    .sheet(isPresented: $isLocationTrackingToggled) {
                        LocationTrackingView(
                            isBestAccuracy: self.$viewModel.isBestAccuracy,
                            isOn: self.$viewModel.isLocationOn
                        )
                }
            }
            .padding()
            .navigationBarTitle(Text("Recognition"))
        }
    }
}

struct LocationTrackingView: View {
    @Binding var isBestAccuracy: Bool
    @Binding var isOn: Bool

    var body: some View {
        NavigationView {
            VStack {
                Text("Please select location accuracy")
                    .fontWeight(.bold)
                    .lineLimit(nil)
                Toggle(isOn: self.$isBestAccuracy) {
                    Text("Location tracking best accuracy")
                }
                Toggle(isOn: self.$isOn) {
                    Text("Location tracking")
                }
            }
            .padding()
            .navigationBarTitle(Text("Location"))
        }
    }
}

struct RecognitionView_Previews: PreviewProvider {
    static var previews: some View {
        RecognitionView(viewModel: RecognitionViewModel())
    }
}
