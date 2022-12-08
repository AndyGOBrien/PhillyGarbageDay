//
//  AddressInputScreen.swift
//  iosApp
//
//  Created by Andy O'Brien on 12/6/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import shared
import Combine

struct AddressInputScreen: View {
    
    @ObservedObject var viewModel = ViewModels().getAddressViewModel().asObserveableObject()
    @State private var address: String = ""
    @State private var showTrashDay: Bool = false

    var body: some View {
        NavigationView {
            VStack {
                NavigationLink(destination: TrashDayScreen().navigationTitle("Trash Day"), isActive: $showTrashDay) { }
                addressInput()
                submitButton()
            }.padding(36)
                .navigationTitle("Enter Address")
        }.navigationViewStyle(.stack)
            .onAppear()
            .onReceive(viewModel.$uiEvent, perform: { event in
                switch event {
                case is Navigation.ShowTrashDay:
                    print("Navigate To TrashDay Screen")
                    showTrashDay = true
                default:
                    showTrashDay = false
                }
            })
        
    }
    
    private func addressInput() -> some View {
        return VStack {
            TextField(
                "Address",
                text: $address,
                onCommit: {
                    viewModel.viewModel.dispatch(
                        action: AddressInputScreenAction.UserSubmittedAddress(address: address)
                    )
                }
            ).onChange(of: address) { newValue in
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
                action: AddressInputScreenAction.UserSubmittedAddress(address: address)
            )
        }).padding(8)
    }
}

struct AddressInputScreen_Previews: PreviewProvider {
    static var previews: some View {
        AddressInputScreen()
    }
}
