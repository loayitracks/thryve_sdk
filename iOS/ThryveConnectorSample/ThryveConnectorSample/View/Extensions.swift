//
//  Extensions.swift
//  SampleCore
//
//  Created by Florian on 03.11.19.
//  Copyright © 2019 mHealthPioneers. All rights reserved.
//

import SwiftUI

extension UIColor {
    convenience init(hex: Int, alpha: CGFloat = 1.0) {
        self.init(
            red: CGFloat((hex & 0xFF0000) >> 16) / 255.0,
            green: CGFloat((hex & 0x00FF00) >> 8) / 255.0,
            blue: CGFloat((hex & 0x0000FF) >> 0) / 255.0,
            alpha: alpha
        )
    }
}
extension Color {
    static var thryve: Color {
        return Color(UIColor(hex: 0x3A95C5))
    }
}
