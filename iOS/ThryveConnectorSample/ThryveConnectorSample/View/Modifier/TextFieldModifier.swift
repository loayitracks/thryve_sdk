//
//  TextFieldModifier.swift
//  SampleCore
//
//  Created by Florian on 04.12.19.
//  Copyright © 2019 mHealthPioneers. All rights reserved.
//

import Foundation
import SwiftUI

struct TextFieldModifier: ViewModifier {
    func body(content: Content) -> some View {
        return content
            .textFieldStyle(RoundedBorderTextFieldStyle())
            .frame(width: 300, height: 50, alignment: .center)
    }
}
