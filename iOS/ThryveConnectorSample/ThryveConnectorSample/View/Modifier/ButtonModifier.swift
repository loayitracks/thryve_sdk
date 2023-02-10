//
//  ButtonModifier.swift
//  SampleCore
//
//  Created by Florian on 20.11.19.
//  Copyright © 2019 mHealthPioneers. All rights reserved.
//

import SwiftUI

struct ButtonModifier: ViewModifier {
    let width: CGFloat

    init(width: CGFloat = 300) {
        self.width = width
    }

    func body(content: Content) -> some View {
        return content
            .padding(5)
            .foregroundColor(Color.thryve)
            .overlay(
                RoundedRectangle(cornerRadius: 5)
                    .stroke(Color.thryve, lineWidth: 1)
                    .frame(width: width, height: 40, alignment: Alignment.center)
        )
    }
}
