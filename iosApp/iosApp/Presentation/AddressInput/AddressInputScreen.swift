//
//  AddressInputScreen.swift
//  iosApp
//
//  Created by Andy O'Brien on 12/6/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import shared

struct AddressInputScreen: View {
    
    @ObservedObject var viewModel = ViewModels().getAddressViewModel().asObserveableObject()
    @State private var username: String = ""

    var body: some View {
        NavigationView {
            switch(viewModel.event) {
            case is Navigation.ShowTrashDay:
                TrashDayScreen()
                    .navigationTitle("Trash Day")
            default:
                VStack {
                    addressInput()
                    submitButton()
                }.padding(36)
                    .navigationTitle("Enter Address")
            }
        }.navigationViewStyle(.stack)
    }
    
    private func addressInput() -> some View {
        return VStack {
            TextField(
                "Address",
                text: $username,
                onCommit: {
                    viewModel.viewModel.dispatch(
                        action: AddressInputScreenAction.UserSubmittedAddress(address: username)
                    )
                }
            ).onChange(of: username) { newValue in
                viewModel.viewModel.dispatch(
                    action: AddressInputScreenAction.UserInputAddressText(text: newValue)
                )
            }.textFieldStyle(.roundedBorder)
            
            if (viewModel.state.addressInputError != nil) {
                Text(viewModel.state.addressInputError ?? "")
                    .font(.caption)
            }
        }
    }
    
    private func submitButton() -> some View {
        return Button("Submit", action: {
            viewModel.viewModel.dispatch(
                action: AddressInputScreenAction.UserSubmittedAddress(address: username)
            )
        }).padding(8)
    }
}

struct AddressInputScreen_Previews: PreviewProvider {
    static var previews: some View {
        AddressInputScreen()
    }
}
